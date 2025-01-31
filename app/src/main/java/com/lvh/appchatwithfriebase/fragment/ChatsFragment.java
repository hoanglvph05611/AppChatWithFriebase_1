package com.lvh.appchatwithfriebase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lvh.appchatwithfriebase.R;
import com.lvh.appchatwithfriebase.adapter.UserAdapter;
import com.lvh.appchatwithfriebase.model.Chat;
import com.lvh.appchatwithfriebase.model.ChatList;
import com.lvh.appchatwithfriebase.model.User;
import com.lvh.appchatwithfriebase.notifycation.Token;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<ChatList> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //   reference = FirebaseDatabase.getInstance().getReference("Chats");

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatList chatList1 = snapshot.getValue(ChatList.class);
                    usersList.add(chatList1);
                }
                if (firebaseUser != null) {
                    chatList();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                usersList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if (chat.getSender().equals(firebaseUser.getUid())) {
//                        usersList.add(chat.getReceiver());
//                    }
//                    if (chat.getReceiver().equals(firebaseUser.getUid())) {
//                        usersList.add(chat.getSender());
//                    }
//
//                }
//                readChats();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    for (ChatList chatList : usersList) {
                        if (user.getId().equals(chatList.getId())) {
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void readChats() {
//        mUsers = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    String value = "";
//                    for (String id: usersList){
//                        if (user.getId().equals(id)){
//                            if (mUsers.size() != 0){
//                                User user1 = mUsers.get(0);
//                                if (!user.getId().equals(user1.getId()) && user.getId() != value){
//                                    value = user.getId();
//                                    mUsers.add(user);
//                                }
//                            }else {
//                                mUsers.add(user);
//                            }
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(), mUsers, true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
