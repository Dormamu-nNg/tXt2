package com.sangaihte.txt2.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sangaihte.txt2.databinding.ItemContainerRecentChatBinding;
import com.sangaihte.txt2.listeners.ConversationListener;
import com.sangaihte.txt2.models.ChatMessage;
import com.sangaihte.txt2.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConvoViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages,ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener = conversationListener;
    }

    @NonNull
    @Override
    public ConvoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConvoViewHolder(
                ItemContainerRecentChatBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConvoViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConvoViewHolder extends RecyclerView.ViewHolder{
        ItemContainerRecentChatBinding binding;

        ConvoViewHolder(ItemContainerRecentChatBinding itemContainerRecentChatBinding){
            super(itemContainerRecentChatBinding.getRoot());
            binding = itemContainerRecentChatBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getCovoImage(chatMessage.convoImage));
            binding.textName.setText(chatMessage.convoName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.convoId;
                user.name = chatMessage.convoName;
                user.image = chatMessage.convoImage;
                conversationListener.onConversationClicked(user);

            });
        }
    }

    private Bitmap getCovoImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }
}
