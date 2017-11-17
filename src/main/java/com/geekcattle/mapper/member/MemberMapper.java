/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.mapper.member;

import com.geekcattle.model.member.Member;
import com.geekcattle.util.CustomerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
@Service
public interface MemberMapper extends CustomerMapper<Member> {
    Member selectByUsername(String username);
}
