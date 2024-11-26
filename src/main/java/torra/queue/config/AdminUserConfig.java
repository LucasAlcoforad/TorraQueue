package torra.queue.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import torra.queue.entity.Admin;
import torra.queue.repository.AdminRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final AdminRepository adminRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(AdminRepository adminRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var admin = adminRepository.findByName("Torra");
        admin.ifPresentOrElse(
                user -> {System.out.println("Admin ja existe");},
                () -> {
                    Admin user = new Admin(
                            null,
                            "Torra",
                            "Flow",
                            "canallucasaquino@gmail.com",
                            null,
                            bCryptPasswordEncoder.encode("MelhorCafeteria")
                    );
                    adminRepository.save(user);
                }
        );
    }
}
