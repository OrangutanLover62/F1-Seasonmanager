const newSeason = (function () {

    $(document).ready(function() {
          // Your callback function to run when the document is loaded
          newSeason.getTracks();
          // Add your code here
        });

    $('#driverSegment').on('ionChange', function (event) {
        $('#newDriver').hide();
        $('#searchDriver').hide();
        $('#' + event.originalEvent.detail.value).show();
    });

    $('#alertNewDriver').on('ionAlertDidDismiss', function () {
        this.isOpen = false;
    });

    $('#alertSearchDriver').on('ionAlertDidDismiss', function () {
        this.isOpen = false;
    });

    function addDriver(id) {
        if (id) {
            api.htmlByDriverId(id).done(function (response) {
                $('#allDrivers').append(response);
            });
        }
    }

    function addTrack(id) {
        if (id) {
            api.htmlByTrackId(id).done(function (response) {
                $('#selectedTracks').append(response);
            });
        }
    }

    return {
        generateSeasonId: function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
            .replace(/[xy]/g, function (c) {
                const r = Math.random() * 16 | 0,
                    v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        },
        newDriver: function (newDriver, allDrivers) {
            api.createDriver(newDriver)
                .done(function (response) {
                    allDrivers.append(response.html);
                })
                .fail(function () {
                    $('#alertNewDriver').get(0).isOpen = true;
                });
        },
        searchDriver: function (searchDriver) {
            searchDriver.find('.searchResultView').remove();
            api.htmlByDriverName(searchDriver.find('ion-input').val())
                .done(function (response) {
                    searchDriver.append(response);
                    searchDriver.find('.searchResultView').on('click', function (event) {
                        addDriver($(event.target).closest('ion-item').attr('id'));
                    });
                })
                .fail(function () {
                    $('#alertSearchDriver').get(0).isOpen = true;
                });
        },
        getTracks: function () {
            api.htmlAllTracks()
                .done(function (response) {
                    response.forEach(function (trackData){
                        var wrapper = $(trackData.html);
                        $('#allTracks').append(wrapper);
                        wrapper.on('click', function () {
                            api.htmlRaceCreator(trackData).done(function (response){
                            var newWrapper = $(response.html);
                            $('#selectedTracks').append(newWrapper);
                            });
                        });
                    });
                });
        },

        createSeason: async function () {
            var seasonId = newSeason.generateSeasonId();
            var races = [];
            var raceFragments = document.getElementsByClassName('raceCreationView');
                for (var i = 0; i < raceFragments.length; i++) {
                    var fragment = raceFragments[i];

                    // Extract the race ID from the fragment ID
                    var raceId = fragment.id.replace('raceFragment', '');

                    // Read the laps value for the current fragment
                    var lapsValue = document.getElementById('laps' + raceId).value;

                    let track = {
                        id: document.getElementById('trackId' + raceId).value,
                        name: document.getElementById('trackName' + raceId).value,
                        country: document.getElementById('trackCountry' + raceId).value,
                        imageBase64: document.getElementById('trackImage' + raceId).value
                    };
                    var raceDtos = await api.postRaceDto(raceId, seasonId, lapsValue, track);
                    races.push(raceDtos);
                }

            var drivers = [];
            var driverFragments = document.getElementsByClassName('driverView');
                for (var i = 0; i < driverFragments.length; i++) {
                    var fragment = driverFragments[i];
                    var driverDtos = await api.getDriverDto(fragment.id);
                    drivers.push(driverDtos);
                }

            var seasonName = document.getElementById('seasonName').value;
            api.createSeason(seasonName, races, drivers);

        }
    }
})();
