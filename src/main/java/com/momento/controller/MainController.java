//정용준 등: 이 코드는 Spring Framework를 사용하여 웹 애플리케이션의
//         컨트롤러(MainController)를 정의하는 부분입니다. 이 컨트롤러는
//         여러 경로에 대한 요청을 처리하고 해당 경로에 대한 뷰를 반환합니다.

package com.momento.controller;

import com.momento.constant.ProductTheme;
import com.momento.dto.MainProductDto;
import com.momento.dto.ProductSearchDto;
import com.momento.service.ProductService;
import com.momento.vo.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping(value = "/")
    public String main(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/main";
    }

    @GetMapping(value = "/product")
    public String theme(ProductSearchDto mainProductSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<MainProductDto> products = productService.getMainProductPage(mainProductSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", mainProductSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/theme";
    }

    //    @GetMapping("/theme/{theme}")
    @GetMapping(value = "/anime")
    public String anime(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.ANIME);
        Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/animation";
    }

    @GetMapping(value = "/holiday")
    public String holiday(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.HOLIDAY);
        var products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/holiday";
    }

    @GetMapping(value = "/vintage")
    public String vintage(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.VINTAGE);
        var products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/vintage";
    }

    @GetMapping(value = "/wedding")
    public String wedding(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.WEDDING);
        var products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/wedding";
    }

    @GetMapping(value = "/traditional")
    public String traditional(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.TRAD_COSTUME);
        var products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/traditional";
    }

    @GetMapping(value = "/etc")
    public String etc(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        productSearchDto.setSearchTheme(ProductTheme.ETC);
        var products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/theme/etc";
    }

    List<Room> roomList = new ArrayList<Room>();
    static int roomNumber = 0;

    @RequestMapping("/chat")
    public ModelAndView chat() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat");
        return mv;
    }

    /**
     * 방 페이지
     * @return
     */
    @GetMapping("/room")
    public ModelAndView room() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("room");
        return mv;
    }

    /**
     * 방 생성하기
     * @param params
     * @return
     */
    @PostMapping("/createRoom")
    public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
        System.out.println("----- create room -------");
        String roomName = (String) params.get("roomName");
        if(roomName != null && !roomName.trim().equals("")) {
            Room room = new Room();
            room.setRoomNumber(++roomNumber);
            room.setRoomName(roomName);
            roomList.add(room);
        }
        return roomList;
    }

    /**
     * 방 정보가져오기
     * @param params
     * @return
     */
    @PostMapping("/getRoom")
    public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params){
        System.out.println("-----get room-----");
        return roomList;
    }

    /**
     * 채팅방
     * @return
     */
    @RequestMapping("/moveChating")
    public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
        ModelAndView mv = new ModelAndView();
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));

        List<Room> new_list = roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors.toList());
        if(new_list != null && new_list.size() > 0) {
            mv.addObject("roomName", params.get("roomName"));
            mv.addObject("roomNumber", params.get("roomNumber"));
            mv.setViewName("chat");
        }else {
            mv.setViewName("room");
        }
        return mv;
    }

    @GetMapping(value = "/history")
    public String history(){
        return "thymeleaf/history/history";
    }

    @GetMapping(value = "/chatgpt")
    public String chatGpt(){
        return "thymeleaf/chatGpt/index";
    }

    @GetMapping(value = "/chatbot.js")
    public String chatbot(){
        return "thymeleaf/chatGpt/chatbot.js";
    }

}