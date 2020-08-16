package com.ljqweb.community_rap.service;

import com.ljqweb.community_rap.dto.NotificationDTO;
import com.ljqweb.community_rap.dto.PageinationDTO;
import com.ljqweb.community_rap.enums.NotificationStatusEnum;
import com.ljqweb.community_rap.enums.NotificationTypeEnum;
import com.ljqweb.community_rap.exception.CustomizeErrorCode;
import com.ljqweb.community_rap.exception.CustomizeException;
import com.ljqweb.community_rap.mapper.NotificationMapper;
import com.ljqweb.community_rap.mapper.UserMapper;
import com.ljqweb.community_rap.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;
    public PageinationDTO list(Long userId, Integer page, Integer size) {
        //size(page-1)
        PageinationDTO<NotificationDTO> pageinationDTO = new PageinationDTO<>();
        Integer totalPage;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }
        if(page<1){
            page =1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageinationDTO.setPageination(totalPage,page);

        List<Notification> notifications = new ArrayList<>();
        Integer offset=size*(page-1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
//        if(size*(page-1)<0){
//            notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 0));
//        }else{
//            offset = size*(page-1);
//
//            notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
//        }
        //List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        example.setOrderByClause("gmt_create desc");
        notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if(notifications.size()==0){
            return pageinationDTO;
        }
//        Set<Long> disUserIds =  notifications.stream().map(notify->notify.getNotifier()).collect(Collectors.toSet());
//        List<Long> userIds = new ArrayList<>(disUserIds);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification:notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
            
        }
        pageinationDTO.setData(notificationDTOS);
        return pageinationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return  notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId()) ){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
