package mvc.codejava.service;


import mvc.codejava.entity.Room;
import mvc.codejava.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	public void save(Room room) {
		roomRepository.save(room);
	}

	public Room get(Long id) {
		return roomRepository.findById(id).get();
	}

	public void delete(Long id) {
		roomRepository.deleteById(id);
	}
}
