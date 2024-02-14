const editSeason = (function () {

    return {
        editRace: async function (raceId, seasonId) {
            var driverIds = [];
            var season = await api.getSeasonDto(seasonId);
            season.drivers.forEach((driver) => {
              driverIds.push(driver.id);
              console.log(driver.id);
            });
            navigation.editRace(raceId, driverIds);
        }
    };
})();