package com.geekcattle.service.member;

import com.geekcattle.mapper.member.MemberMapper;
import com.geekcattle.model.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * author geekcattle
 * date 2017/3/23 0023 上午 11:25
 */
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Integer getCount(Example example){
        return memberMapper.selectCountByExample(example);
    }

    public Member findByUsername(String username) {
        return memberMapper.selectByUsername(username);
    }

    public void insert(Member member){
        memberMapper.insert(member);
    }
}
