package com.geekcattle.mapper.member;

import com.geekcattle.model.member.Member;
import com.geekcattle.core.CustomerMapper;
import org.springframework.stereotype.Service;

/**
 * @author geekcattle
 */
@Service
public interface MemberMapper extends CustomerMapper<Member> {
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Member selectByUsername(String username);
}
