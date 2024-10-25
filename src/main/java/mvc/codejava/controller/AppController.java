package mvc.codejava.controller;

import java.util.List;

// Nhập các lớp cần thiết từ các gói khác
import mvc.codejava.repository.ProductRepository;
import mvc.codejava.service.ProductService;
import mvc.codejava.repository.UserRepository;
import mvc.codejava.entity.Product;
import mvc.codejava.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
	// Inject ProductService để xử lý các yêu cầu liên quan đến sản phẩm
	@Autowired
	private ProductService service;

	// Inject UserRepository để xử lý các yêu cầu liên quan đến người dùng
	@Autowired
	private UserRepository userRepository;

	// Inject ProductRepository để tương tác với cơ sở dữ liệu sản phẩm
	@Autowired
	private ProductRepository productRepository;

	// Phương thức xử lý yêu cầu truy cập trang chính
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		// Kiểm tra xem người dùng với ID 2 có tồn tại hay không
		if(!userRepository.existsById(2L)){
			// Nếu không, tạo một số người dùng mới
			for(int i = 2; i < 5; i++){
				User user = new User();
				user.setEnabled(true); // Kích hoạt người dùng
				user.setUsername("loan" + i); // Gán tên người dùng
				userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
			}
			// Tạo người dùng đầu tiên
			User user = new User();
			user.setEnabled(true);
			user.setUsername("loan");
			user.setId(1L); // Gán ID cho người dùng

			// Mã hóa mật khẩu trước khi lưu
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode("123456"); // Mã hóa mật khẩu "123456"
			user.setPassword(encodedPassword); // Gán mật khẩu đã mã hóa cho người dùng
		}

		// Kiểm tra xem sản phẩm với ID 1 có tồn tại hay không
		if(!productRepository.existsById(1L)){
			// Nếu không, tạo một số sản phẩm mới
			for(int i = 1; i < 10; i++){
				Product p = new Product();
				p.setBrand("Apple " + i); // Gán thương hiệu sản phẩm
				p.setMadein("China " + i); // Gán nơi sản xuất
				p.setPrice(10 + i); // Gán giá sản phẩm
				p.setName("iPhone " + (5 + i)); // Gán tên sản phẩm
				productRepository.save(p); // Lưu sản phẩm vào cơ sở dữ liệu
			}
		}

		// Lấy danh sách tất cả sản phẩm từ dịch vụ
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts); // Thêm danh sách sản phẩm vào mô hình

		return "index"; // Trả về tên view để hiển thị trang chính
	}

	// Phương thức hiển thị form thêm sản phẩm mới
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product(); // Tạo đối tượng sản phẩm mới
		model.addAttribute("product", product); // Thêm sản phẩm vào mô hình

		return "new_product"; // Trả về tên view cho form thêm sản phẩm
	}

	// Phương thức lưu sản phẩm mới
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product); // Gọi dịch vụ để lưu sản phẩm

		return "redirect:/"; // Chuyển hướng về trang chính sau khi lưu
	}

	// Phương thức hiển thị form chỉnh sửa sản phẩm
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product"); // Tạo đối tượng ModelAndView cho view chỉnh sửa

		Product product = service.get(id); // Lấy sản phẩm theo ID
		mav.addObject("product", product); // Thêm sản phẩm vào mô hình

		return mav; // Trả về ModelAndView để hiển thị form chỉnh sửa
	}

	// Phương thức xóa sản phẩm
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		service.delete(id); // Gọi dịch vụ để xóa sản phẩm

		return "redirect:/"; // Chuyển hướng về trang chính sau khi xóa
	}
}
