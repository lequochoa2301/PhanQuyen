package mvc.codejava.configuration;

// Nhập các lớp cần thiết từ các gói khác
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mvc.codejava.entity.Role;
import mvc.codejava.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Lớp MyUserDetails triển khai giao diện UserDetails của Spring Security
public class MyUserDetails implements UserDetails {

	private User user; // Đối tượng người dùng được lưu trữ

	// Constructor để khởi tạo MyUserDetails với một đối tượng User
	public MyUserDetails(User user) {
		this.user = user; // Gán đối tượng User cho thuộc tính user
	}

	// Phương thức trả về danh sách quyền của người dùng
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles(); // Lấy tập hợp các vai trò của người dùng
		List<SimpleGrantedAuthority> authorities = new ArrayList<>(); // Khởi tạo danh sách quyền

		// Duyệt qua từng vai trò và thêm vào danh sách quyền
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName())); // Thêm quyền vào danh sách
		}

		return authorities; // Trả về danh sách quyền
	}

	// Phương thức trả về mật khẩu của người dùng
	@Override
	public String getPassword() {
		return user.getPassword(); // Trả về mật khẩu
	}

	// Phương thức trả về tên người dùng
	@Override
	public String getUsername() {
		return user.getUsername(); // Trả về tên người dùng
	}

	// Phương thức kiểm tra xem tài khoản đã hết hạn hay chưa
	@Override
	public boolean isAccountNonExpired() {
		return true; // Luôn trả về true, nghĩa là tài khoản không hết hạn
	}

	// Phương thức kiểm tra xem tài khoản có bị khóa hay không
	@Override
	public boolean isAccountNonLocked() {
		return true; // Luôn trả về true, nghĩa là tài khoản không bị khóa
	}

	// Phương thức kiểm tra xem thông tin xác thực có hết hạn hay không
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Luôn trả về true, nghĩa là thông tin xác thực không hết hạn
	}

	// Phương thức kiểm tra xem tài khoản có được kích hoạt hay không
	@Override
	public boolean isEnabled() {
		return user.isEnabled(); // Trả về trạng thái kích hoạt của người dùng
	}
}
