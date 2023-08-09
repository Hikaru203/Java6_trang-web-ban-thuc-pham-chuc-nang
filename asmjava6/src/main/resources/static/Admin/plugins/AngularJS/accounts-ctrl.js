const host = "http://localhost:8080";
var queryString = new URLSearchParams(window.location.search);
var jsonData = queryString.get('data');
var form = JSON.parse(decodeURIComponent(jsonData));
var fileNameLabel = document.querySelector('.custom-file-label');

app.controller("myCtrl", function ($scope, $http, $window) {
	$scope.form = {};
	$scope.items = [];
	$scope.currentPage = 1;
	$scope.itemsPerPage = 2;
	$scope.isSubmitting = false;
	$scope.isSubediting = true;
	$scope.formErrors = {}; // Khởi tạo biến lưu trữ các thông báo lỗi
  $scope.reset = function () {
    // Đặt giá trị mặc định cho các biến hoặc xóa dữ liệu nếu cần thiết.
    $scope.form = {};
    $scope.users = [];
  };

  $scope.load_all = function () {
    $http.get(host + "/ManagedAccount").then(resp => {
      $scope.users = resp.data;
      if (form != null) {
        $scope.form = form;
        $scope.isSubmitting = true;
        $scope.isSubediting = false;
      }
    }).catch(error => {
      console.log(error);
    });
  };

  $scope.totalPages = function () {
    if (!$scope.users || !Array.isArray($scope.users)) {
      // Handle the case when $scope.users is not defined or not an array
      return 0;
    }
  
    return Math.ceil($scope.users.length / $scope.itemsPerPage);
  };
  

  $scope.setPage = function (page) {
    if (page >= 1 && page <= $scope.totalPages()) {
      $scope.currentPage = page;
    }
  };

  $scope.isCurrentPage = function (page) {
    return $scope.currentPage === page;
  };

  $scope.getCurrentPageUsers = function () {
    if (!$scope.users || !Array.isArray($scope.users)) {
      // Handle the case when $scope.users is not defined or not an array
      return [];
    }
  
    const startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
    const endIndex = startIndex + $scope.itemsPerPage;
  
    // Check if a search keyword is provided
    const searchKeyword = $scope.searchKeyword ? $scope.searchKeyword.toLowerCase() : '';
  
    const filteredUsers = $scope.users.filter(user => {
      const fullName = user.fullName ? user.fullName.toLowerCase() : '';
      const userName = user.userName ? user.userName.toLowerCase() : '';
      const email = user.email ? user.email.toLowerCase() : '';
      return fullName.includes(searchKeyword) || userName.includes(searchKeyword) || email.includes(searchKeyword);
    });
  
    return filteredUsers.slice(startIndex, endIndex);
  };
  
  

  $scope.getPagesRange = function () {
    const totalPages = $scope.totalPages();
    const range = [];
    const min = Math.max(1, $scope.currentPage - 2);
    const max = Math.min(totalPages, $scope.currentPage + 2);

    for (let i = min; i <= max; i++) {
      range.push(i);
    }

    return range;
  };
  $scope.edit = function (id) {
    var url = host + `/ManagedAccount/${id}`; // Thay 'ManagedProduct' bằng 'ManagedAccount'
    $http.get(url).then(resp => {
      var userToEdit = resp.data; // Lưu thông tin người dùng nhận được từ phản hồi vào biến userToEdit  
      // Điều hướng đến trang chỉnh sửa với thông tin người dùng
      $window.location.href = `${host}/admin/EditAccount/${id}?data=${encodeURIComponent(JSON.stringify(userToEdit))}`;
      $scope.form = form;
    }).catch(error => {
      console.log(error);
    });
  };

  $scope.create = function () {
    // Kiểm tra và gán thông báo lỗi vào biến formErrors
    $scope.formErrors = {};
    var item = angular.copy($scope.form);
    var url = host + '/ManagedAccount';

    // Gọi hàm validateProduct để kiểm tra và lấy thông báo lỗi
    var isValid = validateAccount(item);
    if (!isValid) {
      return; // Nếu có lỗi, không submit form
    }

    alert("Thêm thành công");
    $http.post(url, item).then(resp => {
      $scope.reset();
      $scope.load_all();
      console.log("thanh cong", resp);
    }).catch(error => {
      console.log(error);
    });
  };

  $scope.update = function () {
    $scope.formErrors = {};
		var item = angular.copy($scope.form);
		var url = host + `/ManagedAccount/${$scope.form.id}`;
		console.log(url);
    var isValid = validateAccount(item);
    console.log($scope.form.id);
    if (!isValid) {
			console.error("Validation failed");
			return;
		}
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id === $scope.form.id);
			console.log(index);
			$scope.items[index] = resp.data;
			$window.location.href = `${host}/admin/ManagedAccount`;
			console.log("thanh cong", resp);
		}).catch(error => {
			console.log(error);
		});
	};

  $scope.editRemove = function (id) {
		var url = host + `/ManagedAccount/${id}`;
		$http.get(url).then(resp => {
			$scope.form = resp.data;
			$scope.form.active = false;
			console.log($scope.form);
			$scope.update();
		}).catch(error => {
			console.log(error);
		});
	};

  function validateAccount(Account) {
    // Đặt lại formErrors để xóa các thông báo lỗi cũ
    $scope.formErrors = {};

    // Kiểm tra trường họ và tên
    if (!$scope.form.fullName) {
      $scope.formErrors.fullName = "Vui lòng nhập họ và tên.";
    }

    // Kiểm tra trường email
    if (!$scope.form.email) {
      $scope.formErrors.email = "Vui lòng nhập địa chỉ email.";
    }

    // Kiểm tra trường username
    if (!$scope.form.userName) {
      $scope.formErrors.userName = "Vui lòng nhập tên người dùng.";
    }

    // Kiểm tra trường password
    if (!$scope.form.password) {
      $scope.formErrors.password = "Vui lòng nhập mật khẩu.";
    }

    

    // Nếu không có lỗi, trả về true để cho phép submit form
    return Object.keys($scope.formErrors).length === 0;
  };
  // Load users data on controller initialization
  $scope.load_all();
});
