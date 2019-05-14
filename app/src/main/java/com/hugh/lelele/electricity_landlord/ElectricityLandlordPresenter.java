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

    private int mMonth;
    private int mYear;

    private final String TAG = ElectricityLandlordPresenter.class.getSimpleName();

    public ElectricityLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                      @NonNull ElectricityLandlordContract.View electricityLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mElectricityLandlordtView = checkNotNull(electricityLandlordView, "electricityTenantView cannot be null!");
        mElectricityLandlordtView.setPresenter(this);

        mMonth = Calendar.getInstance().get(Calendar.MONTH);
        mYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public int getMonthIndex(int month) {
        int monthIndex;
        if (month == 0) {
            monthIndex = 12;
        } else {
            monthIndex = month;
        }
        return monthIndex;
    }

    @Override
    public String getMonthBeUpdated(int month) {
        String monthBeUpdated;
        if (month == 0) {
            monthBeUpdated = "12";
        } else {
            if (month < 10) {
                monthBeUpdated = "0" + String.valueOf(month);
            } else {
                monthBeUpdated = String.valueOf(month);
            }
        }
        return monthBeUpdated;
    }

    @Override
    public String getMonthBeUpdatedNext(int month) {
        String monthBeUpdatedNext;
        if (month < 9) {
            monthBeUpdatedNext = "0" + String.valueOf(month + 1);
        } else {
            monthBeUpdatedNext = String.valueOf(month + 1);
        }
        return monthBeUpdatedNext;
    }

    @Override
    public String getYearBeUpdated(int month, int year) {
        String yearBeUpdated;
        if (month == 0) {
            yearBeUpdated = String.valueOf(year - 1);
        } else {
            yearBeUpdated = String.valueOf(year);
        }
        return yearBeUpdated;
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
        //call RoomsElectricityRecursive to get electric fee for each room.
        new RoomsElectricityRecursive(rooms, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(),
                getYearBeUpdated(mMonth, mYear),
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

        checkNoEmptyElectricityData(rooms);
    }

    @Override
    public void showLastFragment() {

    }

    @Override
    public void hideKeyBoard() {

    }

    private void checkNoEmptyElectricityData(ArrayList<Room> rooms) {

        int count = 0;
        for (Room room:rooms) {

            if (room.getElectricities().get(getMonthIndex(mMonth)).getScale().equals("")) {
                mElectricityLandlordtView.showHasEmptyDataToast();
                break;
            } else {
                count++;
                if (count == rooms.size()) {
                    mElectricityLandlordtView.toBackStack();
                    sendElectricityArticles(rooms);
                    uploadElectricity2Rooms(rooms);
                    pushNotifications(rooms);
                }
            }

        }
    }

    private void uploadElectricity2Rooms(ArrayList<Room> rooms) {
        for (Room room:rooms) {

            //this month
            uploadElectricity(UserManager.getInstance().getLandlord().getEmail(),
                    UserManager.getInstance().getUserData().getGroupNow(),
                    room.getRoomName(), getYearBeUpdated(mMonth, mYear),
                    getMonthBeUpdated(mMonth),
                    room.getElectricities().get(getMonthIndex(mMonth)));

            //next month
            Electricity electricityNext = new Electricity();
            electricityNext.setScaleLast(room.getElectricities().get(getMonthIndex(mMonth)).getScale());
            uploadElectricity(UserManager.getInstance().getLandlord().getEmail(),
                    UserManager.getInstance().getUserData().getGroupNow(),
                    room.getRoomName(), String.valueOf(mYear),
                    getMonthBeUpdatedNext(mMonth),
                    electricityNext);
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

                pushNotification(room, email);
            }
        }
    }

    private void pushNotification(Room room, String email) {
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
