package com.hugh.lelele.messaging_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.util.ImageManager;

import java.util.ArrayList;

import static com.google.android.gms.internal.firebase_messaging.zzg.checkNotNull;

public class MessagingListAdapter extends RecyclerView.Adapter {

    private MessagingListContract.Presenter mPresenter;

    private ArrayList<Room> mRooms;
    private ArrayList<Room> mNotEmptyRooms;

    public MessagingListAdapter(MessagingListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class MessagingListViewHolder extends RecyclerView.ViewHolder {

        ImageView senderPicture;
        TextView senderName;
        TextView latestMessage;
        TextView roomName;

        public MessagingListViewHolder(@NonNull View itemView) {
            super(itemView);

            senderPicture = itemView.findViewById(R.id.item_image_sender_picture_messaging);
            senderPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().getResources().
                    getDimensionPixelOffset(R.dimen.radius_author_avatar)));

            senderName = itemView.findViewById(R.id.item_text_view_his_name_messaging_list);
            latestMessage = itemView.findViewById(R.id.item_text_view_latest_message_messaging_list);
            roomName = itemView.findViewById(R.id.item_text_view_room_name_messaging_list);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_message_list_holder, viewGroup, false);

        MessagingListViewHolder viewHolder = new MessagingListViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Room room = mNotEmptyRooms.get(i);

        if (room.getTenant().isBinding()) {
            ArrayList<Message> messages = room.getMessages();

            ((MessagingListViewHolder) viewHolder).senderName.setText(room.getTenant().getName());

            ((MessagingListViewHolder) viewHolder).roomName.setText(room.getTenant().getRoomNumber());

            ((MessagingListViewHolder) viewHolder).latestMessage.setText(messages.get(messages.size() - 1).getContent());

            ImageManager.getInstance().setImageByUrl(((MessagingListViewHolder) viewHolder).senderPicture,
                    room.getTenant().getPicture());
        }

    }

    @Override
    public int getItemCount() {

        if (mRooms != null) {
            return numberOfTenants(mRooms);
        } else {
            return 0;
        }
    }

    private int numberOfTenants(ArrayList<Room> rooms) {

        int count = 0;

        for (Room room:rooms) {
            if (room.getTenant().isBinding()) {
                count++;
            }
        }
        return count;
    }

    public void updateData(ArrayList<Room> rooms) {
        mRooms = checkNotNull(rooms);
        getNotEmptyRooms();
//        notifyDataSetChanged();
    }

    private void getNotEmptyRooms() {

        ArrayList<Room> notEmptyRooms = new ArrayList<>();

        for (Room room:mRooms) {
            if (room.getTenant().isBinding()) {
                notEmptyRooms.add(room);
            }
        }

        mNotEmptyRooms = notEmptyRooms;
        notifyDataSetChanged();
    }
}
