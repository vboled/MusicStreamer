package vboled.netcracker.musicstreamer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.Playlist;
import vboled.netcracker.musicstreamer.model.user.User;
import vboled.netcracker.musicstreamer.repository.PlaylistRepository;
import vboled.netcracker.musicstreamer.repository.RegionRepository;
import vboled.netcracker.musicstreamer.repository.UserRepository;
import vboled.netcracker.musicstreamer.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final String USER_PATTERN = "^[a-zA-z]+(\\w{0,30})$";

    private final String EMAIL_PATTERN =
            "^([A-Za-z\\d])([\\w-.]{0,20})([A-Za-z\\d])@([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d].)*" +
            "([A-Za-z\\d]([A-Za-z\\d-])*[A-Za-z\\d])" +
            "(.[A-Za-z]+)$";

    private final String PHONE_PATTERN = "^\\+(\\d{1,4})(\\d{8})$";

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final PlaylistRepository playlistRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RegionRepository regionRepository, PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkEmail(user.getEmail());
        checkPhone(user.getPhoneNumber());
        checkUserName(user.getUserName());
        return userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(Long id) throws NoSuchElementException {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean update(User user, Long id) {
        if (userRepository.existsById(id)) {
            checkEmail(user.getEmail());
            checkPhone(user.getPhoneNumber());
            checkUserName(user.getUserName());
            user.setId(id);
            user.setCreateDate(userRepository.getById(id).getCreateDate());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEmail(String email, Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setEmail(email);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePhone(String phone, Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setPhoneNumber(phone);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String password, Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void checkEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException("Email is null");
        if (!email.toLowerCase(Locale.ROOT).matches(EMAIL_PATTERN))
            throw new IllegalArgumentException("Wrong Email format!!!");
        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email is already taken!!!");
    }

    @Override
    public void checkPhone(String phone) {
        if (phone == null || !phone.matches(PHONE_PATTERN))
            throw new IllegalArgumentException("Wrong phone format!!!");
        if (userRepository.existsByPhoneNumber(phone))
            throw new IllegalArgumentException("Phone Number is already taken!!!");
    }

    @Override
    public void checkUserName(String userName) {
        if (userName == null || !userName.matches(USER_PATTERN))
            throw new IllegalArgumentException("Wrong username format!!!");
        if (userRepository.existsByUserName(userName))
            throw new IllegalArgumentException("Username is already taken!!!");
    }

    @Override
    public boolean isValidUserName(String userName) {
        return userName.matches(USER_PATTERN);
    }

    @Override
    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches(PHONE_PATTERN);
    }

    @Override
    public User getByUPE(String UPE) {
        if (UPE == null)
            throw new NoSuchElementException("NULL");
        if (UPE.matches(USER_PATTERN))
            return getByUserName(UPE);
        if (UPE.matches(EMAIL_PATTERN))
            return getByEmail(UPE);
        if (UPE.matches(PHONE_PATTERN))
            return getByPhoneNumber(UPE);
        throw new NoSuchElementException("Can't detect type");
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).get();
    }

    @Override
    public User userNotFullUpdate(User update, Long id) {
        User updated = updateCommonFields(update, id);
        userRepository.save(updated);
        return updated;
    }

    private User updateCommonFields(User update, Long id) throws UsernameNotFoundException, IllegalArgumentException {
        if (!userRepository.existsById(id))
            throw new UsernameNotFoundException("No user with such id");
        User userToUpdate = userRepository.getById(id);
        if (update.getUserName() != null) {
            if (userRepository.existsByUserName(update.getUserName()))
                throw new IllegalArgumentException("UserName is taken");
            userToUpdate.setUserName(update.getUserName());
        }
        if (update.getName() != null)
            userToUpdate.setName(update.getName());
        if (update.getLastName() != null)
            userToUpdate.setLastName(update.getLastName());
        return userToUpdate;
    }

    @Override
    public User userFullUpdate(User update) {
        User userToUpdate = updateCommonFields(update, update.getId());
        if (update.getPassword() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(update.getPassword()));
        }
        if (update.getRegion() != null) {
            userToUpdate.setRegion(update.getRegion());
        }
        if (update.getEmail() != null) {
            checkEmail(update.getEmail());
            userToUpdate.setEmail(update.getEmail());
        }
        if (update.getPhoneNumber() != null) {
            checkPhone(update.getPhoneNumber());
            userToUpdate.setPhoneNumber(update.getPhoneNumber());
        }
        if (update.getCreateDate() != null) {
            userToUpdate.setCreateDate(update.getCreateDate());
        }
        if (update.getLastLoginDate() != null) {
            userToUpdate.setLastLoginDate(update.getLastLoginDate());
        }
        if (update.getPlayListID() != -1) {
            userToUpdate.setPlayListID(update.getPlayListID());
        }
        if (update.getRole() != null) {
            userToUpdate.setRole(update.getRole());
        }
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public boolean matches(String hashed, String common) {
        return passwordEncoder.matches(hashed, common);
    }

    @Override
    public String encode(String toHash) {
        return passwordEncoder.encode(toHash);
    }

    @Override
    public List<Playlist> getAllPlaylists(Long id) {
        return playlistRepository.findAllPlaylistsByOwner(id);
    }
}
