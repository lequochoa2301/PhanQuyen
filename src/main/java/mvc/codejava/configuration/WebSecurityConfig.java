package mvc.codejava.configuration;

// Nhập các lớp cần thiết từ các gói khác
import mvc.codejava.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Đánh dấu lớp này là một cấu hình Spring
@Configuration
// Bật chức năng bảo mật web
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Bean cung cấp đối tượng UserDetailsService
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(); // Trả về một thể hiện của UserDetailsServiceImpl
	}

	// Bean cung cấp BCryptPasswordEncoder để mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Trả về một thể hiện của BCryptPasswordEncoder
	}

	// Bean cung cấp DaoAuthenticationProvider để xác thực người dùng
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Tạo mới đối tượng DaoAuthenticationProvider
		authProvider.setUserDetailsService(userDetailsService()); // Đặt UserDetailsService cho authProvider
		authProvider.setPasswordEncoder(passwordEncoder()); // Đặt PasswordEncoder cho authProvider

		return authProvider; // Trả về đối tượng authProvider
	}

	// Cấu hình AuthenticationManagerBuilder để sử dụng authProvider
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider()); // Sử dụng DaoAuthenticationProvider
	}

	// Cấu hình HttpSecurity để quản lý quyền truy cập
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // Bắt đầu cấu hình các yêu cầu được ủy quyền
				.antMatchers("/rooms").permitAll() // Cho phép mọi người truy cập vào /student
				.antMatchers("/new").hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN và CREATOR truy cập vào /new
				.antMatchers("/edit/**").hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN và EDITOR truy cập vào /edit/**
				.antMatchers("/delete/**").hasAuthority("ADMIN") // Chỉ cho phép ADMIN truy cập vào /delete/**
				.anyRequest().authenticated() // Tất cả các yêu cầu khác phải được xác thực
				.and()
				.formLogin().permitAll() // Cho phép mọi người truy cập vào trang đăng nhập
				.and()
				.logout().permitAll() // Cho phép mọi người truy cập vào chức năng đăng xuất
				.and()
				.exceptionHandling().accessDeniedPage("/403"); // Chỉ định trang 403 khi truy cập bị từ chối
	}
}
