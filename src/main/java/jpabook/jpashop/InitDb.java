package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 2개
 *  userA
 *  JPA1 BOOK
 *  JPA2 BOOK
 *  userB
 *  Spring BOOK1
 *  Spring BOOK2
 */
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA","서울","1","1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000,10);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK",20000,20);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);


            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = createMember("userB","진주","2","2222");
            em.persist(member);

            Book book1 = createBook("SpringBook1",30000,120);
            em.persist(book1);

            Book book2 = createBook("SpringBook2",40000,210);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 4);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 3);


            Delivery delivery = createDelivery(member);
            Order order2 = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order2);
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private  Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street,zipcode));
            return member;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }





}
