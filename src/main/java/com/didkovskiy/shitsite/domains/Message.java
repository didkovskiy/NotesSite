package com.didkovskiy.shitsite.domains;

import com.didkovskiy.shitsite.domains.userstore.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String text;
    private String tag;
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    public String getMessageAuthor(){
        return author.getUsername();
    }
}
