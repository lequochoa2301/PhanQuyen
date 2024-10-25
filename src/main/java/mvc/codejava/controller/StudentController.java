package mvc.codejava.controller;

// Nhập các lớp cần thiết từ các gói khác
import mvc.codejava.entity.StudentEntity;
import mvc.codejava.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller // Đánh dấu lớp này là một Controller trong Spring MVC
public class StudentController {

	@Autowired // Tự động tiêm đối tượng StudentRepository
	StudentRepository studentRepository;

	// Phương thức hiển thị biểu mẫu thêm sinh viên
	@RequestMapping("/student")
	public String showNewStudentForm(Model model) {
		return "index_student"; // Trả về trang HTML cho biểu mẫu sinh viên
	}

	// Phương thức xử lý việc chèn sinh viên mới và ảnh
	@PostMapping(value = "/insertImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
	public ModelAndView save(@RequestParam("name") String name,
							 @RequestParam("age") Integer age,
							 @RequestPart("photo") MultipartFile photo) {
		try {
			// Tạo một đối tượng StudentEntity mới
			StudentEntity student = new StudentEntity();
			student.setName(name); // Đặt tên cho sinh viên
			student.setAge(age); // Đặt tuổi cho sinh viên
			student.setPhoto(photo.getBytes()); // Lưu ảnh của sinh viên dưới dạng byte
			studentRepository.save(student); // Lưu sinh viên vào cơ sở dữ liệu
			return new ModelAndView("redirect:/fetch"); // Chuyển hướng đến trang hiển thị danh sách sinh viên
		} catch (Exception e) {
			// Nếu có lỗi xảy ra, trả về trang sinh viên với thông báo lỗi
			return new ModelAndView("student", "msg", "Error: " + e.getMessage());
		}
	}

	// Phương thức hiển thị danh sách sinh viên
	@GetMapping(value = "/fetch")
	public ModelAndView listStudent(ModelAndView model) throws IOException {
		List<StudentEntity> listStu = (List<StudentEntity>) studentRepository.findAll(); // Lấy danh sách tất cả sinh viên
		model.addObject("listStu", listStu); // Thêm danh sách sinh viên vào mô hình
		model.setViewName("student"); // Chỉ định trang hiển thị là "student"
		return model; // Trả về mô hình
	}

	// Phương thức lấy ảnh của sinh viên theo ID
	@GetMapping(value = "/getStudentPhoto/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody // Đánh dấu rằng phương thức này trả về dữ liệu
	public ResponseEntity<byte[]> getStudentPhoto(@PathVariable("id") long id) {
		// Tìm kiếm sinh viên theo ID
		StudentEntity student = studentRepository.findById(id).orElse(null);
		// Nếu sinh viên tồn tại và có ảnh
		if (student != null && student.getPhoto() != null) {
			return ResponseEntity.ok() // Trả về phản hồi thành công
					.contentType(MediaType.IMAGE_JPEG) // Đặt loại nội dung là hình ảnh JPEG
					.body(student.getPhoto()); // Gửi ảnh của sinh viên
		} else {
			return ResponseEntity.notFound().build(); // Nếu không tìm thấy, trả về phản hồi 404
		}
	}
}
