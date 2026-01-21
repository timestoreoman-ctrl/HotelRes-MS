const API_BASE = "http://localhost:9001";

async function fetchRooms() {
    const res = await fetch(`${API_BASE}/rooms`);
    return await res.json();
}

async function createRoom(roomData) {
    const res = await fetch(`${API_BASE}/rooms`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(roomData)
    });
    return await res.json();
}

async function fetchRoomById(id) {
    const res = await fetch(`${API_BASE}/rooms/${id}`);
    return await res.json();
}
