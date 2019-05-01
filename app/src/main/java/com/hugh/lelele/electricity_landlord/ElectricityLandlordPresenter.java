package com.hugh.lelele.electricity_landlord;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.data.source.RoomsElectricityRecursive;
import com.hugh.lelele.data.source.RoomsElectricityRecursiveCallback;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordPresenter implements ElectricityLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final ElectricityLandlordContract.View mElectricityLandlordtView;
    ArrayList<Room> mRooms;

    private final String TAG = ElectricityLandlordPresenter.class.getSimpleName();

    public ElectricityLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                      @NonNull ElectricityLandlordContract.View electricityLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mElectricityLandlordtView = checkNotNull(electricityLandlordView, "electricityTenantView cannot be null!");
        mElectricityLandlordtView.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void setRoomData(ArrayList<Room> rooms) {

        //ToDo: replace the landlord info by user manager.
        //call RoomsElectricityRecursive to get electric fee for each room.
        new RoomsElectricityRecursive(rooms, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(),
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                mLeLeLeRepository, new RoomsElectricityRecursiveCallback() {
            @Override
            public void onCompleted(ArrayList<Room> roomArrayList) {
                mRooms = roomArrayList;
                mElectricityLandlordtView.showElectricityEditorUi(mRooms);
                Log.v(TAG, "RoomsElectricityRecursive Working");
            }
        });
    }

    @Override
    public void uploadElectricity(String landlordEmail, String groupName, String roomName,
                                  String year, String month, Electricity electricity) {
        mLeLeLeRepository.uploadElectricityData(landlordEmail, groupName, roomName, year, month, electricity);
    }

    @Override
    public void initialElectricityMonth(String landlordEmail, String groupName, String roomName, String year, String month) {
        mLeLeLeRepository.initialElectricityMonthData(landlordEmail, groupName, roomName, year, month);
    }

    @Override
    public void checkRoomData(ArrayList<Room> rooms) {
        new RoomsElectricityRecursive(rooms, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(),
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                mLeLeLeRepository, new RoomsElectricityRecursiveCallback() {
            @Override
            public void onCompleted(ArrayList<Room> roomArrayList) {
                mRooms = roomArrayList;
                checkNoEmptyElectricityData(mRooms);
                Log.v(TAG, "RoomsElectricityRecursive Working");
            }
        });
    }

    @Override
    public void showLastFragment() {

    }

    @Override
    public void hideKeyBoard() {

    }

    private void checkNoEmptyElectricityData(ArrayList<Room> rooms) {
        int month = Calendar.getInstance().get(Calendar.MONTH);

        int count = 0;
        for (Room room:rooms) {

            if (room.getElectricities().get(month).getScale().equals("")) {
                mElectricityLandlordtView.showHasEmptyDataToast();
                break;
            } else {
                count++;
                if (count == rooms.size()) {
                    mElectricityLandlordtView.toBackStack();
                    sendElectricityArticles(rooms);
                    pushNotifications(rooms);
                }
            }

        }
    }

    private void sendElectricityArticles(ArrayList<Room> rooms) {

        for (Room room:rooms) {
            if (room.getTenant().isBinding()) {
                Article article = new Article();
                Electricity electricity = room.getElectricities().get(Calendar.getInstance().get(Calendar.MONTH));
                article.setTitle(LeLeLe.getAppContext().getString(R.string.electricity_fee_update_notification));
                article.setContent(LeLeLe.getAppContext().getString(R.string.electricity_content,
                        room.getTenant().getName(),
                        electricity.getPrice()));
                article.setType(Constants.ELECTRICITY);
                article.setAuthor(UserManager.getInstance().getLandlord().getName());
                article.setAuthorEmail(UserManager.getInstance().getLandlord().getEmail());
                article.setAuthorPicture(UserManager.getInstance().getLandlord().getPicture());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                article.setTime(String.valueOf(formatter.format(Calendar.getInstance().getTime())));

                mLeLeLeRepository.sendTenantArticle(article, room.getTenant().getEmail());
            }
        }
    }

    private void pushNotifications(ArrayList<Room> rooms) {

        for (Room room:rooms) {
            String email = room.getTenant().getEmail();
            if (room.getTenant().isBinding()) {
//                Map<String, Object> notificationMessage = new HashMap<>();
//                notificationMessage.put(Constants.NOTIFICATION_CONTENT, LeLeLe.getAppContext().getString(R.string.electricity_content,
//                        room.getTenant().getName(),
//                        electricity.getPrice()));
//                notificationMessage.put(Constants.NOTIFICATION_SENDER_EMAIL, UserManager.getInstance().getUserData().getEmail());
//                notificationMessage.put(Constants.NOTIFICATION_TITLE, LeLeLe.getAppContext().getString(R.string.electricity_fee_update_notification));

                Electricity electricity = room.getElectricities().get(Calendar.getInstance().get(Calendar.MONTH));
                long time= System.currentTimeMillis();

                Notification notification = new Notification();
                notification.setSenderEmail(UserManager.getInstance().getUserData().getEmail());
                notification.setTitle(LeLeLe.getAppContext().getString(R.string.electricity_fee_update_notification));
                notification.setContent(LeLeLe.getAppContext().getString(R.string.electricity_content,
                        room.getTenant().getName(),
                        electricity.getPrice()));
                notification.setNotificationType(Constants.ELECTRICITY);
                notification.setTimeMillisecond(time);

                mLeLeLeRepository.pushNotificationToTenant(notification, email, new LeLeLeDataSource.PushNotificationCallback() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "pushNotifications onCompleted: ");
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
            }
        }
    }
}
