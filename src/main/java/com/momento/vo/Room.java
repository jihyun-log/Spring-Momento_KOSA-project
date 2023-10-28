//정용준: Room 클래스는 간단한 자바 값 객체(VO)로, 방을 나타내는 클래스입니다.
//       roomNumber와 roomName 두 개의 속성이 포함되어 있습니다.
//       이 클래스는 시스템 내의 방과 관련된 데이터를 보관하고 조작하기 위해 사용됩니다.

package com.momento.vo;

public class Room {
    int roomNumber;
    String roomName;

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", roomName=" + roomName + "]";
    }
}