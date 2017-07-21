package com.com.ycj.talk;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Content {

    private String userId;

    private String friendId;

    private String content;


    @NonNull
    private Commend commend;


}
