package com.wuxiu.forum.cache;

import com.wuxiu.forum.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {

    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTDs = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","java","node","python","c","c++"));
        tagDTDs.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台架构");
        framework.setTags(Arrays.asList("spring","mybatis","springMvc","springSecurity","springData"));

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","tomcat","apache"));
        tagDTDs.add(server);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql","redis","oracle","sqlserver","nosql"));
        tagDTDs.add(database);

        TagDTO utils = new TagDTO();
        utils.setCategoryName("工具");
        utils.setTags(Arrays.asList("git","github","maven","idea","svn"));
        tagDTDs.add(utils);
        return tagDTDs;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOs = get();
        List<String> tigList = tagDTOs.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tigList.contains(t)).collect(Collectors.joining(","));
       return invalid;
    }

}
