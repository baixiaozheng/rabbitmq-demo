package com.baixiaozheng.rabbitmqcommon.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(chain = true)
public class MessageVo implements Serializable {

    private static final long serialVersionUID = 7423008006747226416L;

    private String id;

    private String message;
}
