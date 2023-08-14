app.controller("distributor-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.lockbtnAdd = false;
    $scope.lockbtnDelete = true;
    $scope.lockbtnUpdate = true;
    $scope.initialize = function () {
        // load brands
        $http.get("/rest/distributors").then(resp => {
            $scope.items = resp.data;
        });
    }

    // Khởi đầu
    $scope.initialize();

    // Xóa form
    $scope.reset = function () {
        $scope.form = {};
        $scope.lockbtnAdd = false;
        $scope.lockbtnDelete = true;
        $scope.lockbtnUpdate = true;
    }

    // Hiển thị lên form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(1)").tab('show');
        $scope.lockbtnAdd = true;
        $scope.lockbtnDelete = false;
        $scope.lockbtnUpdate = false;
    }

    // Thêm  mới 
    $scope.create = function () {
        if ($scope.validate()) {
            var item = angular.copy($scope.form);
            $http.post('/rest/distributors', item).then(resp => {
                $scope.items.push(resp.data);
                $scope.reset();
                alert("Thêm mới thành công!");
            }).catch(error => {
                alert("Lỗi thêm mới!");
                console.log("Error", error)
            })
        }
    }

    // Cập nhật 
    $scope.update = function () {
        if ($scope.validate()) {
            var item = angular.copy($scope.form);
            $http.put(`/rest/distributors/${item.id}`, item).then(resp => {
                var index = $scope.items.findIndex(
                    p => p.id == item.id);
                $scope.items[index] = item;
                alert("Cập nhật thành công!");
            })
                .catch(error => {
                    alert("Lỗi cập nhật!");
                    console.log("Error", error)
                })
        }
    }

    // Xóa 
    $scope.delete = function (item) {
        $http.delete(`/rest/distributors/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(
                p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa thành công!");
        })
            .catch(error => {
                alert("Lỗi xóa!");
                console.log("Error", error)
            })
    }

    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },

        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }

    //validate
    $scope.brandMessages = {
        id: "ID is required",
        nameNull: "Tên  nhà phân phối không được rỗng",
        nameNaN: "Tên  nhà phân phối không được là số",
        phoneNull: "số điện thoại không được rỗng",
        phoneNaN: "số điện thoại sai định dạng",
        addressNull: "Địa chỉ không được rỗng",
        // ...
    };

    $scope.validate = function () {
        $scope.message1 = "";
        $scope.message2 = "";
        $scope.message3 = "";
        $scope.checkV = false;
        if ($scope.form.name === undefined) {
            $scope.hassError = true;
            $scope.message1 = $scope.brandMessages.nameNull;
            $scope.checkV = true;
        }
        if (!isNaN($scope.form.name)) {
            $scope.hassError = true;
            $scope.message1 = $scope.brandMessages.nameNaN;
            $scope.checkV = true;
        }
        if ($scope.form.contactInfo === undefined) {
            $scope.hassError = true;
            $scope.message2 = $scope.brandMessages.phoneNull;
            $scope.checkV = true;
        }
        // Biểu thức chính quy để kiểm tra số điện thoại Việt Nam
        var phoneNumberPattern = /^(?:\+84|0)(?:3[2-9]|5[6-9]|7[0-9]|8[1-9]|9[0-9])[0-9]{7}$/;

        // Kiểm tra số điện thoại
        if (!phoneNumberPattern.test($scope.form.contactInfo)) {
            $scope.hasError = true;
            $scope.message2 = $scope.brandMessages.phoneNaN;
            $scope.checkV = true;
        }
        if ($scope.form.address === undefined) {
            $scope.hassError = true;
            $scope.message3 = $scope.brandMessages.addressNull;
            $scope.checkV = true;
        }
        if ($scope.checkV === true) {
            return false;
        } else {
            return true;
        }

    }
    function getCookieValue(cookieName) {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith(cookieName + '=')) {
                return cookie.substring(cookieName.length + 1);
            }
        }
        return null;
    }

    const usernameCookie = getCookieValue('id');
    if (usernameCookie !== null) {
        console.log('Giá trị của cookie username là:', usernameCookie);
    } else {
        console.log('Cookie username không tồn tại.');
    }
    $scope.user = function () {
        $http.get("http://localhost:8080/ManagedAccountByUserName/" + usernameCookie).then(resp => {
            $scope.userLogin = resp.data;
            console.log($scope.userLogin);
        }).catch(error => {
            console.log(error);
        });
    };
    $scope.user();

})