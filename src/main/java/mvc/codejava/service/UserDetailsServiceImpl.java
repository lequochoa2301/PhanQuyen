package mvc.codejava.service; // Gói chứa lớp dịch vụ cho ứng dụng

import mvc.codejava.configuration.MyUserDetails; // Nhập lớp MyUserDetails để sử dụng cho việc tạo đối tượng UserDetails
import mvc.codejava.entity.User; // Nhập lớp User để thao tác với đối tượng người dùng
import mvc.codejava.repository.UserRepository; // Nhập lớp UserRepository để truy cập dữ liệu người dùng
import org.springframework.beans.factory.annotation.Autowired; // Nhập annotation để tự động tiêm phụ thuộc
import org.springframework.security.core.userdetails.UserDetails; // Nhập lớp UserDetails từ Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Nhập lớp UserDetailsService để tạo lớp dịch vụ cho người dùng
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Nhập exception để xử lý khi không tìm thấy người dùng

// Định nghĩa lớp UserDetailsServiceImpl thực hiện giao diện UserDetailsService
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired // Tự động tiêm đối tượng UserRepository
	private UserRepository userRepository;

	// Phương thức loadUserByUsername để lấy thông tin người dùng theo tên đăng nhập
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// Lấy người dùng từ cơ sở dữ liệu theo tên đăng nhập
		User user = userRepository.getUserByUsername(username);

		// Kiểm tra nếu người dùng không tồn tại
		if (user == null) {
			// Ném ngoại lệ nếu không tìm thấy người dùng
			throw new UsernameNotFoundException("Could not find user");
		}

		// Trả về đối tượng MyUserDetails chứa thông tin người dùng
		return new MyUserDetails(user);
	}
}
