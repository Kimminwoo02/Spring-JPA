package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception{
    //given
    Member member = new Member();
    member.setName("Kim");

    //when
    Long savedId = memberService.join(member);

    //then
    assertEquals(member,memberRepository.findOne(savedId));
    }
    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() throws Exception{
    //given
        Member member = new Member();
        member.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
    //when
        memberService.join(member);
        memberService.join(member2);
    //then
        Assert.fail("예외가 발생해야 한다.");
}
}