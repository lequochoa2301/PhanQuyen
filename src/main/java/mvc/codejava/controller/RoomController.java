package mvc.codejava.controller;

import mvc.codejava.entity.Room;
import mvc.codejava.entity.User;
import mvc.codejava.repository.RoomRepository;
import mvc.codejava.repository.UserRepository;
import mvc.codejava.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/rooms")
    public String viewHomePage(Model model) {
        List<Room> listRooms = roomService.list();
        model.addAttribute("listRooms", listRooms);
        return "room_list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/rooms/new")
    public String showNewRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "new_room";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/rooms/save")
    public String saveRoom(@ModelAttribute("room") Room room,
                           @RequestParam("photoFile") MultipartFile photoFile) {
        if (!photoFile.isEmpty()) {
            try {
                room.setPhoto(photoFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        roomService.save(room);
        return "redirect:/rooms";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/rooms/edit/{id}")
    public String showEditRoomForm(@PathVariable("id") Long id, Model model) {
        Room room = roomService.get(id);
        model.addAttribute("room", room);
        return "edit_room";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/rooms/update/{id}")
    public String updateRoom(@PathVariable("id") Long id,
                             @ModelAttribute("room") Room room,
                             @RequestParam("photoFile") MultipartFile photoFile) {
        if (!photoFile.isEmpty()) {
            try {
                room.setPhoto(photoFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        room.setRoomId(id);
        roomService.save(room);
        return "redirect:/rooms";
    }

    @RequestMapping("/rooms/photo/{id}")
    @ResponseBody
    public byte[] getRoomPhoto(@PathVariable Long id) {
        Room room = roomService.get(id);
        return room.getPhoto();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
        return "redirect:/rooms";
    }

}
