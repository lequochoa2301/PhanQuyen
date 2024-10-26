package mvc.codejava.service; // Gói chứa lớp dịch vụ cho ứng dụng

import mvc.codejava.configuration.MyUserDetails; // Nhập lớp MyUserDetails để sử dụng cho việc tạo đối tượng UserDetails
import mvc.codejava.entity.User; // Nhập lớp User để thao tác với đối tượng người dùng
import mvc.codejava.repository.UserRepository; // Nhập lớp UserRepository để truy cập dữ liệu người dùng
import org.springframework.beans.factory.annotation.Autowired; // Nhập annotation để tự động tiêm phụ thuộc
import org.springframework.security.core.userdetails.UserDetails; // Nhập lớp UserDetails từ Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Nhập lớp UserDetailsService để tạo lớp dịch vụ cho người dùng
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Nhập exception để xử lý khi không tìm thấy người dùng


public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User user = userRepository.getUserByUsername(username);


		if (user == null) {

			throw new UsernameNotFoundException("Could not find user");
		}


		return new MyUserDetails(user);
	}
}
