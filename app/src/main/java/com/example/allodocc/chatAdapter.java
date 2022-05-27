package com.example.allodocc;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allodoc.databinding.ItemContainerReceivedMessageBinding;
import com.example.allodoc.databinding.ItemContainerSentMessageBinding;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
         private final List<chatMessage> chatMessage ;
         private  String senderId ;
        private String UIDreceiver ;
        public static final int VIEW_TYPE_SENT =1 ;
        public static final int VIEW_TYPE_RECEIVED =2 ;

    public chatAdapter(List<com.example.allodocc.chatMessage> chatMessage, String senderId, String UIDreceiver) {
        this.chatMessage = chatMessage;
        this.senderId = senderId;
        this.UIDreceiver = UIDreceiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else {
             return new ReceivedMessageViewHolder(
                ItemContainerReceivedMessageBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessage.get(position));
        } else{
            ((ReceivedMessageViewHolder) holder).setData(chatMessage.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }
    @Override
    public int getItemViewType(int position){
        if (chatMessage.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT ;
        }else{
            return VIEW_TYPE_RECEIVED ;
    }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
            private final ItemContainerSentMessageBinding binding ;

            SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
                super(itemContainerSentMessageBinding.getRoot());
                binding=itemContainerSentMessageBinding;
            }
            void setData(chatMessage chatMessage){
                binding.textMessage.setText(chatMessage.message);
                binding.textDateTime.setText(chatMessage.dateTime);
            }
        }
         static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerReceivedMessageBinding binding ;

        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
            super(itemContainerReceivedMessageBinding.getRoot());
            binding=itemContainerReceivedMessageBinding;
        }
        void setData(chatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);

        }
    }
    }

