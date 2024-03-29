package org.csu.mypetstore.api.service.imp;

import org.csu.mypetstore.api.entity.LineItem;
import org.csu.mypetstore.api.entity.Order;
import org.csu.mypetstore.api.entity.OrderStatus;
import org.csu.mypetstore.api.entity.Sequence;
import org.csu.mypetstore.api.persistence.*;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.vo.LineItemVO;
import org.csu.mypetstore.api.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private LineItemMapper lineItemMapper;

    public OrderVO getOrderVO(String orderId){

        OrderStatus orderStatus =orderStatusMapper.selectById(orderId);
        Order order = orderMapper.selectById(orderId);
        OrderVO orderVO = orderToOrderVO(order,orderStatus);
        return orderVO;
    }

    private OrderVO orderToOrderVO(Order order, OrderStatus orderStatus){

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(order.getOrderId());
        orderVO.setUsername(order.getUsername());
        orderVO.setOrderDate(order.getOrderDate());
        orderVO.setShipAddress1(order.getShipAddress1());
        orderVO.setShipAddress2(order.getShipAddress2());
        orderVO.setShipCity(order.getShipCity());
        orderVO.setShipState(order.getShipState());
        orderVO.setShipZip(order.getShipZip());
        orderVO.setShipCountry(order.getShipCountry());
        orderVO.setBillAddress1(order.getBillAddress1());
        orderVO.setBillAddress2(order.getBillAddress2());
        orderVO.setBillCity(order.getBillCity());
        orderVO.setBillState(order.getBillState());
        orderVO.setBillZip(order.getBillZip());
        orderVO.setBillCountry(order.getBillCountry());
        orderVO.setCourier(order.getCourier());
        orderVO.setTotalPrice(order.getTotalPrice());
        orderVO.setBillToFirstName(order.getBillToFirstName());
        orderVO.setBillToLastName(order.getBillToLastName());
        orderVO.setShipToFirstName(order.getShipToFirstName());
        orderVO.setShipToLastName(order.getShipToLastName());
        orderVO.setCreditCard(order.getCreditCard());
        orderVO.setExpiryDate(order.getExpiryDate());
        orderVO.setCardType(order.getCardType());
        orderVO.setLocale(order.getLocale());


        orderVO.setStatus(orderStatus.getStatus());
        return orderVO;
    }

    public int getNextOrderId(){
        Sequence ordernum = sequenceMapper.selectById("ordernum");
        return ordernum.getNextId()+1;
    }

    public void updateNextOrderId(){
        Sequence ordernum = sequenceMapper.selectById("ordernum");
        ordernum.setNextId(ordernum.getNextId()+1);
        sequenceMapper.updateById(ordernum);
    }

    public void InsertOrderVOToDB(OrderVO orderVO){

        //更新sequence表
        updateNextOrderId();

        //插入order表
        Order order = orderVOToOrder(orderVO);
        orderMapper.insert(order);
        //插入orderStatus表
        OrderStatus orderStatus = orderVOToOrderStatus(orderVO);
        orderStatusMapper.insert(orderStatus);

        //插入orderStatus表
        LineItem lineItem = new LineItem();
        lineItem.setOrderId(orderVO.getOrderId());
        lineItem.setLineNumber(orderVO.getOrderId());
        List<LineItemVO> lineItems = orderVO.getLineItems();
        Iterator<LineItemVO> lineItemsIterator = lineItems.iterator();
        while(lineItemsIterator.hasNext()){
            LineItemVO lineItemVO = lineItemsIterator.next();
            lineItem.setItemId(lineItemVO.getItemId());
            lineItem.setQuantity(lineItemVO.getQuantity());
            lineItem.setUnitPrice(lineItemVO.getUnitPrice());

            lineItemMapper.insert(lineItem);

        }

    }

    private Order orderVOToOrder(OrderVO orderVO){
        Order order = new Order();
        order.setOrderId(orderVO.getOrderId());
        order.setUsername(orderVO.getUsername());
        order.setOrderDate(orderVO.getOrderDate());
        order.setShipAddress1(orderVO.getShipAddress1());
        order.setShipAddress2(orderVO.getShipAddress2());
        order.setShipToFirstName(orderVO.getShipToFirstName());
        order.setShipToLastName(orderVO.getShipToLastName());
        order.setShipCity(orderVO.getShipCity());
        order.setShipState(orderVO.getShipState());
        order.setShipZip(orderVO.getShipZip());
        order.setShipCountry(orderVO.getShipCountry());
        order.setBillAddress1(orderVO.getBillAddress1());
        order.setBillAddress2(orderVO.getBillAddress2());
        order.setBillCity(orderVO.getBillCity());
        order.setBillState(orderVO.getBillState());
        order.setBillZip(orderVO.getBillZip());
        order.setBillCountry(orderVO.getBillCountry());
        order.setBillState(orderVO.getBillState());
        order.setCourier(orderVO.getCourier());
        order.setTotalPrice(orderVO.getTotalPrice());
        order.setBillToFirstName(orderVO.getBillToFirstName());
        order.setBillToLastName(orderVO.getBillToLastName());
        order.setCreditCard(orderVO.getCreditCard());
        order.setExpiryDate(orderVO.getExpiryDate());
        order.setCardType(orderVO.getCardType());
        order.setLocale(orderVO.getLocale());
        return order;
    }

    private OrderStatus orderVOToOrderStatus(OrderVO orderVO){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderVO.getOrderId());
        orderStatus.setLinenum(orderVO.getOrderId());
        orderStatus.setTimestamp(orderVO.getOrderDate());
        orderStatus.setStatus("P");
        return orderStatus;
    }



}
