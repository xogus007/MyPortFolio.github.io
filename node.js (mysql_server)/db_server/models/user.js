
// 사용자 정보 조회
exports.findUserById = (id) => {
  return users.find((user) => user.id === id);
};

// 사용자 정보 추가
exports.addUser = (user) => {
  users.push(user);
};

// 사용자 정보 수정
exports.updateUser = (id, updatedUser) => {
  users = users.map((user) => {
    if (user.id === id) {
      return { ...user, ...updatedUser };
    }
    return user;
  });
};
