package com.wuxiu.forum.service;

import com.wuxiu.forum.dto.NotificationDTO;
import com.wuxiu.forum.dto.PaginationDTO;
import com.wuxiu.forum.enums.NotificationStatusEnum;
import com.wuxiu.forum.enums.NotificationTypeEnum;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.exception.CustomizeException;
import com.wuxiu.forum.mapper.NotificationMapper;
import com.wuxiu.forum.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    //获取通知列表
    public PaginationDTO list(Long id, Integer pageIndex, Integer pageSize) {
        //分页数据
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //数据传输列表对象
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        //总记录数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer pageCount;
        //总页数
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }
        //判断页面传入参数极值
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageIndex > pageCount) {
            pageIndex = pageCount;
        }
        //分页逻辑判断
        paginationDTO.setPageIation(pageCount, pageIndex);
        //开始查询处
        Integer offSet = pageSize * (pageIndex - 1);
        //查询每页显示的数据
        NotificationExample notificationExample1 = new NotificationExample();
        notificationExample1.createCriteria().andReceiverEqualTo(id);
        notificationExample1.setOrderByClause("GMT_CREATE desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample1, new RowBounds(offSet, pageSize));
        if (notifications.size() == 0){
            return paginationDTO;
        }
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;

    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver() == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        //更新为已读
        notification.setStatus(NotificationStatusEnum.READE.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
