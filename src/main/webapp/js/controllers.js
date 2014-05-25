'use strict';

/* Controllers */

function PreyAdminController($scope, $http, $timeout) {

    $scope.preyTimes = [];
    $scope.selectedPrayTimes = {};
    $scope.selectedDay = 1;
    $scope.selectedMonth = 1;
    $scope.selectedYear = 1;

    $scope.getPrayTimes = function() {
        $http({
            url: '/api/prayer/year/' + $scope.selectedYear + '/month/' + $scope.selectedMonth + '/day/' + $scope.selectedDay,
            method: 'GET',
            headers: 'Content-Type : application/json'
        }).success(function(data) {
            $scope.preyTimes = data;
        });
    }



    $scope.savePrey = function() {
        $scope.preyTimes.year = $scope.selectedYear;
        $scope.preyTimes.month = $scope.selectedMonth;
        $scope.preyTimes.day = $scope.selectedDay;

        $http({
            url: '/admin/api/prayer',
            method: 'POST',
            data: $scope.preyTimes,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(data) {
            $scope.preyTimes = data;
        });
    }

    $scope.addPrey = function() {
        $scope.preyTimes.preylist.push({
            'name': $scope.newPreyName,
            'time': $scope.newPreyTime
        });
    }


    $scope.today = function() {
        $scope.dt = new Date();
        $scope.selectedDay = $scope.dt.getDate();
        $scope.selectedMonth = $scope.dt.getMonth() + 1;
        $scope.selectedYear = $scope.dt.getFullYear();
    };

    $scope.today();
    $scope.getPrayTimes();


    $scope.open = function() {
        $timeout(function() {
            $scope.opened = true;
        });
    };

    $scope.$watch('dt', function() {
        $scope.selectedDay = $scope.dt.getDate();
        $scope.selectedMonth = $scope.dt.getMonth() + 1;
        $scope.selectedYear = $scope.dt.getFullYear();
        $scope.getPrayTimes();

    });

}


function HolidaysAdminController($scope, $http, $timeout,$rootScope) {

    $scope.holidays = [];
    $rootScope.news = false; 
    $rootScope.jumma = true; 
    /*
  $scope.newHolidayName = "";
  $scope.newHolidayFrom; 
  $scope.newHolidayTo;
  $scope.newHolidayAppFrom; 
  $scope.newHolidayAppTo; 
  
  */

    $scope.addHoliday = function() {
        var holiday = {
            fromMonth: $scope.fromMonth,
            toMonth: $scope.toMonth,
            fromDay: $scope.fromDay,
            toDay: $scope.toDay,
            hours: $scope.hours,
            minuttes: $scope.min
        };
        $scope.holidays.push(holiday);
        $scope.saveHolidays();
    };


    /*

  int fromMonth;
  int toMonth;
  int fromDay;
  int toDay;
  DateTime updated;
  int hours; 
  int minuttes; 
  */



    $scope.getHolidays = function() {
        $http({
            url: 'rest/json/jumma',
            method: 'GET',
            headers: 'Content-Type : application/json'
        }).success(function(data) {
            $scope.holidays = data;
        }).error(function(data) {
            alert("Kunne ikke hente fredagsbønner fra serveren. Prøv igjen senere");
        });
    }

    $scope.saveHolidays = function() {
        $http({
            url: 'rest/json/jumma',
            method: 'POST',
            data: $scope.holidays,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(data) {
        	alert("B�nn er n� lageret");
        }).error(function(data) {
            alert("Kunne ikke lagre fredagsbønner til serveren. Prøv igjen senere");
        });
    }

    $scope.removeJumma = function(jumma) {
        $http({
            url: 'rest/json/jumma',
            method: 'DELETE',
            data: jumma,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(data) {
            $scope.getHolidays();
        }).error(function(data) {
            alert("Kunne ikke slette fredagsbønn fra serveren. Prøv igjen senere");
        });
    }


    $scope.getHolidays();

    /*
  $scope.$watch('dt', function() {
    $scope.selectedDay = $scope.dt.getDate();
    $scope.selectedMonth = $scope.dt.getMonth() + 1 ;
    $scope.selectedYear = $scope.dt.getFullYear();
   });
*/

}

function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = true;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path("/");
			});
		});
	};
};

function HeaderController($scope, $rootScope, $location, $cookieStore) {
	

};


function NewsAdminController($scope, $http, $timeout,$upload,$rootScope) {
	$rootScope.jumma = false; 
	$rootScope.news = true; 
	
	
    $scope.news = [];
    $scope.newArticleImage;
    $scope.image;
    $scope.images = [];
    
    $scope.alerts = [
                  
   ];
    
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.priority = [{
        name: 'Forsiden 1',
        value: 6
    }, {
        name: 'Forsiden 2',
        value: 5
    },
    {
        name: 'Forsiden 3',
        value: 4
    },
    {
        name: 'Forsiden 4',
        value: 3
    },{
        name: 'Forsiden 5',
        value: 2
    },
    {
        name: 'Forsiden 6',
        value: 1
    }];

    $scope.category = [{
        name: 'Nyhet',
        value: 1
    }, {
        name: 'Byggeprosjektet',
        value: 2
    }, {
        name: 'Event',
        value: 3
    }, ];


    $scope.filters = [{
        name: 'Nyhet',
        value: 1
    }, {
        name: 'Byggeprosjektet',
        value: 2
    }, {
        name: 'Event',
        value: 3
    }, ];


    $scope.backgroundChange = function(image) {
      for(var i = 0;i<$scope.images.length;i++){
        if($scope.images[i].imageUrl!==image.imageUrl){
          $scope.images[i].background = false;
        }
      }

    };

    $scope.articleChanged = function(image) {
      for(var i = 0;i<$scope.images.length;i++){
        if($scope.images[i].imageUrl!==image.imageUrl){
          $scope.images[i].article = false;
        }
      }

    };
    
    $scope.hasChoosenBackgroundImage = function() {
    	return getBackgroundImage();
    };

    var getBackgroundImage = function(){
      for(var i = 0;i<$scope.images.length;i++){
        if($scope.images[i].background === true){
            return $scope.images[i];
        }
      }
    };

   var getArticleImage = function(){
      for(var i = 0;i<$scope.images.length;i++){
        if($scope.images[i].article === true){
            return $scope.images[i];
        }
      }
    };


    $scope.getNews = function() {
        $http({
            url: 'rest/json/news',
            method: 'GET',
            headers: 'Content-Type : application/json'
        }).success(function(data) {
            $scope.news = data;
        });
    }

    $scope.getNewsWithFilter = function(filter) {
        $http({
            url: 'rest/json/news?filter=' + filter,
            method: 'GET',
            headers: 'Content-Type : application/json'
        }).success(function(data) {
            $scope.news = data;
        });
    }


    //TODO : Post with a query paramter if the image has not been changed. 
    $scope.saveNews = function(news) {
        if ($scope.pri)
            news.pri = $scope.pri.value;
        if ($scope.cat)
            news.cat = $scope.cat.value;

        if(getArticleImage()){
          var articleImage = getArticleImage();
          news.articleImageUrl = articleImage.imageUrl; 
          news.articleImageText = articleImage.imageText; 
        }

        if(getBackgroundImage()){
          var bgImage = getBackgroundImage();
          news.imgUrl = bgImage.imageUrl; 
          news.imageText = bgImage.imageText; 
        };


        var urlToPost = 'rest/admin/news';
        $http({
            url: urlToPost,
            method: 'POST',
            headers: 'Content-Type : application/json',
            data : news
            
        }).success(function(data) {
            //TODO : Loop through all news to see if it's allready exsists in the list, if not add it. 
            if ($scope.filter) {
                $scope.getNewsWithFilter($scope.filter.value);
            } else {
                $scope.getNews();
            }
            $scope.cancelEdit();
            $scope.alerts.push({type : "success", msg: "Nyhet er lagret!"});
            $timeout(function() {$scope.alerts=[];}, 5000);
        }).error(function(error) {
        	 $scope.alerts.push({type : "danger", msg: "Noe gikk galt ved lagring av nyhet. Kontakt Magnar på 46793283"});
        });
    };


    $scope.deleteNews = function(news) {
        $http({
            url: 'rest/admin/news/delete',
            method: 'POST',
            data: news,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(data) {
            //TODO : Loop through all news to see if it's allready exsists in the list, if not add it. 
            if ($scope.filter) {
                $scope.getNewsWithFilter($scope.filter.value);
            } else {
                $scope.getNews();
            }
            alert("Nyhet har blitt slettet fra servern");
        }).error(function(error) {
            alert("Noe gikk galt ved sletting av nyhet. Kontakt Magnar på 46793283");
        });
    };



    $scope.onTimeSet = function(newDate, oldDate) {
        console.log(newDate);
        console.log(oldDate);
    }

    $scope.cancelEdit = function() {
        $scope.isInEditMode = false;
        $scope.selectedNews = {};
        $scope.images = [];
        $scope.image = "";
        $scope.newArticleImage = null;
        $scope.cat = $scope.category[0];
    };

    $scope.editNews = function(news) {
        $scope.isInEditMode = true;
        $scope.imageHasChanged = false;
        $scope.images = [];
        
        if(news.imgUrl){
         
          $scope.images.push({'imageText' : news.imageText, 'background' : true, 'imageUrl' : news.imgUrl});
        }

        if(news.articleImageUrl){
          $scope.images.push({'imageText' : news.articleImageText, 'article' : true, 'imageUrl' : news.articleImageUrl});
        }

        $scope.image = news.imgUrl;
        $scope.selectedNews = news;
        $scope.pri = null;
        $scope.cat = null;
        if (news.pri) {
            for (var i = 0; i < $scope.priority.length; i++) {
                if ($scope.priority[i].value === news.pri) {
                    $scope.pri = $scope.priority[i];
                }
            }
        }
        if (news.cat) {
            for (var i = 0; i < $scope.category.length; i++) {
                if ($scope.category[i].value === news.cat) {
                    console.log("Kommer inn her");
                    $scope.cat = $scope.category[i];
                }
            }
        }

    };


    $scope.onFileSelect = function($files) {
    //$files: an array of files selected, each file has name, size, and type.
    for (var i = 0; i < $files.length; i++) {
      var file = $files[i];
      $scope.upload = $upload.upload({
        url: 'rest/admin/upload', //upload.php script, node.js route, or servlet url
        method: 'POST',
        // headers: {'headerKey': 'headerValue'}, withCredential: true,
        file: file,
        // file: $files, //upload multiple files, this feature only works in HTML5 FromData browsers
        /* set file formData name for 'Content-Desposition' header. Default: 'file' */
        //fileFormDataName: myFile, //OR for HTML5 multiple upload only a list: ['name1', 'name2', ...]
        /* customize how data is added to formData. See #40#issuecomment-28612000 for example */
        //formDataAppender: function(formData, key, val){} 
      }).progress(function(evt) {
        console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
      }).success(function(data, status, headers, config) {
        $scope.images.push(data);
        console.log(data);
      });
      //.error(...)
      //.then(success, error, progress); 
    }
   }; 


    $scope.location = '';





    $scope.isNyhet = function() {
        return $scope.cat && $scope.cat.value === 1;
    };

    $scope.isBygg = function() {
        return $scope.cat && $scope.cat.value === 2;
    };

    $scope.isEvent = function() {
        return $scope.cat && $scope.cat.value === 3;
    };

    $scope.$watch('filter', function() {
        if ($scope.filter) {
            $scope.getNewsWithFilter($scope.filter.value);
        } else {
            $scope.getNews();
        }
    }); // initialize the watch


    $scope.cancelEdit();

}
