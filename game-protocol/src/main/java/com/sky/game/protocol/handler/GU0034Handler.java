package com.sky.game.protocol.handler;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.internal.service.payment.PaymentService;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0034Beans;
import com.sky.game.protocol.commons.GU0034Beans.Request;
import com.sky.game.protocol.commons.GU0034Beans.Response;
import com.sky.game.service.domain.ChannelPayment;
import com.sky.game.service.domain.Store;
import com.sky.game.service.domain.StoreOrder;
import com.sky.game.service.logic.ShopService;
import com.sky.game.service.persistence.ChannelPaymentMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@HandlerType(transcode="GU0034", enable=true, namespace="GameUser")
@Component("GU0034")
public class GU0034Handler extends BaseProtocolHandler<GU0034Beans.Request, GU0034Beans.Response>
{

  @Autowired
  ShopService shopService;

  @Autowired
  PaymentService paymentService;

  @Autowired
  ChannelPaymentMapper channelPaymentMapper;

  @HandlerMethod(enable=true)
  public boolean handler(GU0034Beans.Request req, GU0034Beans.Response res)
    throws ProtocolException
  {
    boolean ret = super.handler(req, res);
    Store store = this.shopService.selectStoreById(req.getId());
    String orderId = "D" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + req.getId();
    String itemName = null;
    if (ret) {
      StoreOrder order = new StoreOrder();
      if (req.getId() != 0L) {
        order.setOrderId(orderId);
        order.setUserAccountId(Long.valueOf(BasePlayer.getPlayer(req).getUserId()));
        if (store.getItemId().longValue() == 1001L) {
          itemName = "金币";
        }
        else if (store.getItemId().longValue() == 1003L) {
          itemName = "钻石";
        }
        order.setDescription(store.getTotalCount() + itemName);
        order.setMenoy(store.getPrice());
        order.setType(store.getPayType());
        order.setStoreId(Long.valueOf(req.getId()));
        this.shopService.insertStroeOrder(order);
      }
      req.getId();
      ChannelPayment channelPayment = this.channelPaymentMapper.selectChannelPaymentByStoreId(req.getId());

      int jfid = channelPayment.getJfId().intValue();
      String decription = channelPayment.getItemDescription();
      String name = channelPayment.getItemName();
      int price = channelPayment.getPrice().intValue();
      HashMap orderInfoMap = this.paymentService.productOrder(orderId, itemName, store.getPrice().intValue() * 100, jfid, decription, name);
      res.setOrderInfoMap(orderInfoMap);
    }
    return ret;
  }
}
