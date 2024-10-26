package mvc.codejava; // Gói chứa lớp PasswordGenerator

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Nhập lớp BCryptPasswordEncoder từ Spring Security để mã hóa mật khẩu

// Định nghĩa lớp PasswordGenerator để tạo và mã hóa mật khẩu
public class PasswordGenerator {

	// Phương thức main là điểm khởi đầu của ứng dụng
	public static void main(String[] args) {
		// Tạo một đối tượng BCryptPasswordEncoder để thực hiện mã hóa
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// Đặt mật khẩu gốc cần mã hóa
		String rawPassword = "123";//admin

		// Mã hóa mật khẩu gốc bằng phương thức encode của BCryptPasswordEncoder
		String encodedPassword = encoder.encode(rawPassword);

		// In ra mật khẩu đã được mã hóa
		System.out.println(encodedPassword);
	}
}
