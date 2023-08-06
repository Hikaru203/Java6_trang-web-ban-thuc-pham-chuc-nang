let host = "http://localhost:8080";
const app = angular.module("myApp", []);
var queryString = window.location.search;
var urlParams = new URLSearchParams(queryString);
var jsonData = urlParams.get('data');
var form = JSON.parse(decodeURIComponent(jsonData));
var fileNameLabel = document.querySelector('.custom-file-label');

// Bây giờ biến formData chứa dữ liệu từ $scope.form truyền từ trang trước.


app.controller("myCtrl", function ($scope, $http, $window) {
  $scope.form = {};
  $scope.items = [];
  $scope.reset = function () {
    $scope.form = {};
    $scope.items = [];
  };
  $scope.load_all = function () {
    $http.get(host + "/ManagedProduct").then(resp => {
      $scope.items = resp.data;

      if (form != null) {
        $scope.form = form; // Assign the value back to $scope.form.
        fileNameLabel.innerText = $scope.form.image;
        $scope.isSubmitting = true;
        $scope.isSubediting = false;


      } else {
        $scope.isSubmitting = false;
        $scope.isSubediting = true;

      }


    }).catch(error => {
      console.log(error);
    });
  };


  $scope.load_all_categories = function () {
    $http.get(host + "/ManagedCategories").then(resp => {
      $scope.Categories = resp.data;

    }).catch(error => {
      console.log(error);
    })
  };
  $scope.edit = function (id) {
    var url = host + `/ManagedProduct/${id}`; // Use backticks (`) instead of quotes ('') around the string.
    $http.get(url).then(resp => {
      $scope.form = resp.data;

      $window.location.href = `${host}/admin/EditProduct/${id}?data=${encodeURIComponent(JSON.stringify($scope.form))}`;
      $scope.form = form; // Gán lại giá trị cho $scope.form.

    }).catch(error => {
      console.log(error);
    });
  };

  $scope.editRemove = function (id) {
    var url = host + `/ManagedProduct/${id}`; // Use backticks (`) instead of quotes ('') around the string.
    $http.get(url).then(resp => {
      $scope.form = resp.data;

      $scope.form.active = false;
      console.log($scope.form);
      $scope.update();
    }).catch(error => {
      console.log(error);
    });
  };

  $scope.create = function () {
    var item = angular.copy($scope.form);
    var url = host + '/ManagedProduct';
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
    var item = angular.copy($scope.form);
    var url = host + `/ManagedProduct/${$scope.form.id}`;
    console.log(url);
    $http.put(url, item).then(resp => {
      var index = $scope.items.findIndex(p => p.id === $scope.form.id);
      console.log(index);
      $scope.items[index] = resp.data;
      $window.location.href = `${host}/admin/index`;
      console.log("thanh cong", resp);
    }).catch(error => {
      console.log(error);
    });
  };



  $scope.uploadedFile = function (files) {
    var data = new FormData();
    data.append('file', files[0]);
    $http.post(host + '/upload/statis', data, {
      transformRequest: angular.identity,
      headers: { "Content-Type": undefined }
    }).then(resp => {
      $scope.form.image = resp.data.name;
    }).catch(error => {
      console.log(error);
    });

    if (files.length > 0) {
      fileNameLabel.innerText = files[0].name;
    } else {
      fileNameLabel.innerText = 'Choose file';
    }
  };


  $scope.load_all();
  $scope.load_all_categories();
  $scope.reset();
});

