package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(){

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/baobao.jpg");
        return fileDTO;
    }
}
