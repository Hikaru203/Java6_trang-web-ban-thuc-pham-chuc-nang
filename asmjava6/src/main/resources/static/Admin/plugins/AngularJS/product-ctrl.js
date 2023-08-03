let host = "http://localhost:8080";
const app = angular.module("myApp", []);

app.controller("myCtrl", function ($scope, $http) {
  $scope.form = {};
  $scope.items = [];
  $scope.reset = function () {

  };
  $scope.load_all = function () {
    $http.get(host + "/ManagedProduct").then(resp => {
      $scope.items = resp.data;
      console.log("thanh cong", resp);

    }).catch(error => {
      console.log(error);
    })
  };
  $scope.edit = function (email) {
    var url = host + `/Students/${email}`; // Use backticks (`) instead of quotes ('') around the string.
    $http.get(url).then(resp => {
      $scope.form = resp.data;
      console.log("thanh cong", resp);

    }).catch(error => {
      console.log(error);
    });
  };

  $scope.create = function () {
    var item = angular.copy($scope.form);
    var url = host + '/ManagedProduct';
    alert("Thêm thành công");
    console.log("thanh cong", resp);

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
    var url = host + `/Students/${$scope.form.email}`;
    $http.put(url, item).then(resp => {
      var index = $scope.items.findIndex(p => p.email === $scope.form.email);
      $scope.items[index] = resp.data;
      console.log("thanh cong", resp);
    }).catch(error => {
      console.log(error);
    });
  };


  $scope.delete = function (email) {
    var url = host + `/Students/${email}`;
    $http.delete(url).then(resp => {
      delete $scope.items[email];
      $scope.reset();
      $scope.load_all();
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
      console.log("thanh cong", resp);
    }).catch(error => {
      console.log(error);
    });
  };

  $scope.load_all();
  $scope.reset();
});

