angular.module('starter.controllers', [])
.controller('welcomeCtrl',function($scope){
    $scope.isLogin = true;
})
.controller('loginCtrl',function($scope){
            $scope.name = "ssss";
            })
.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
})
.controller('ProductListCtrl',function($scope,$stateParams,ProductList){
  $scope.productList = ProductList.all();
    $scope.data = {searchItem:'',
                   searchResult:'',
    };
    $scope.searchProduct = function() {
        ProductList.getByName().get({productName:$scope.data.searchItem}).$promise.then(function(res){
            console.log(res);
        });
        console.log($scope.data.searchResult );
    }

})
.controller('ProductProfileCtrl',function($scope,$stateParams,ProductList){
  $scope.product = ProductList.getById().get({productId:$stateParams.productId});
    $scope.buy = function()
    {
        console.log(this);
    }
})
.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
