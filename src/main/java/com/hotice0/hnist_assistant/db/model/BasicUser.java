package com.hotice0.hnist_assistant.db.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author Hotice0
 */
@Data
@NoArgsConstructor
public class BasicUser  implements Serializable {
    private Integer uuid;
    private Integer role_id;
    private String nick;
    private String avatar;
    private String open_id;
    private String union_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public BasicUser(Integer role_id, String nick, String avatar, String open_id, String union_id) {
        this.role_id = role_id;
        this.nick = nick;
        this.avatar = avatar;
        this.open_id = open_id;
        this.union_id = union_id;
    }
}
