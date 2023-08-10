const app = angular.module('myApp', []);
app.controller("brand-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.initialize = function () {
        // load brands
        $http.get("/rest/brands").then(resp => {
            $scope.items = resp.data;
        });
    }

    // Khởi đầu
    $scope.initialize();

    // Xóa form
    $scope.reset = function () {
        $scope.form = {};
    }

    // Hiển thị lên form
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(1)").tab('show');
    }
   

    // Thêm  mới 
    $scope.create = function () {
        $scope.hassError = false;
        if ($scope.validate()) {
        $scope.hassError = false;
        var item = angular.copy($scope.form);
        $http.post('/rest/brands', item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Thêm mới sản phẩm thành công!");
        }).catch(error => {
            alert("Lỗi thêm mới sản phẩm!");
            console.log("Error", error)
        })
        }
    }

    // Cập nhật 
    $scope.update = function () {
        if ($scope.validate()) {
        var item = angular.copy($scope.form);
        $http.put(`/rest/brands/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(
                p => p.id == item.id);
            $scope.items[index] = item;
            alert("Cập nhật sản phẩm thành công!");
        })
            .catch(error => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error)
            })
        }
    }
    // Xóa 
    $scope.delete = function (item) {
        $http.delete(`/rest/brands/${item.id}`).then(resp => {
            var index = $scope.items.findIndex(
                p => p.id == item.id);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa sản phẩm thành công!");
        })
            .catch(error => {
                alert("Lỗi xóa sản phẩm!");
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
            if(this.page <0){
                this.last();
            }
        },
        next() {
            this.page++;
            if(this.page >=this.count){
                this.first();
            }
        },
        last() {
            this.page = this.count-1;
        }
    }

    //validate
    $scope.brandMessages = {
        id: "ID is required",
        nameNull: "Tên nhãn hàng không được rỗng",
        nameNumber: "Tên nhãn hàng không được điền số",
        originNull: "Nguồn gốc không được rỗng",
        responsibleNameNull: "Tên người chịu trách nhiệm không được rỗng",
        // ...
    };
    
    $scope.validate = function () {
        $scope.message1="";
        $scope.message2="";
        $scope.message3="";
        $scope.checkV=false;
        if ($scope.form.name === undefined) {
            $scope.hassError = true;
            $scope.message1 = $scope.brandMessages.nameNull;
            $scope.checkV=true;
        }
        if (!isNaN($scope.form.name)) {
            $scope.hassError = true;
            $scope.message1 = $scope.brandMessages.nameNumber;
            $scope.checkV=true;
        }
        if ($scope.form.responsibleName === undefined) {
            $scope.hassError = true;
            $scope.message2 = $scope.brandMessages.responsibleNameNull;
            $scope.checkV=true;
        }
        if ($scope.form.origin === undefined) {
            $scope.hassError = true;
            $scope.message3 = $scope.brandMessages.originNull;
            $scope.checkV=true;
        }
        if($scope.checkV===true){
            return false;
        }else{
            return true;
        }
        
    }
   
})
