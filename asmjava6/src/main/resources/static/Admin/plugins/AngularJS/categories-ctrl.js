
app.controller("myCtrl", function ($scope, $http) {
  $scope.form = {};
  $scope.Categories = [];
  $scope.reset = function () {

  };
  $scope.load_all = function () {
    $http.get(host + "/ManagedCategories").then(resp => {
      $scope.Categories = resp.data;

    }).catch(error => {
      console.log(error);
    })
  };
  
  $scope.load_all();
  $scope.reset();
});

