package com.geekcattle.mapper.member;

import com.geekcattle.model.member.Member;
import com.geekcattle.util.CustomerMapper;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
public interface MemberMapper extends CustomerMapper<Member> {
    Member selectByUsername(String username);
}
