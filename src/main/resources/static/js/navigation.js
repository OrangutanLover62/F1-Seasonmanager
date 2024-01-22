const navigation = (function () {

    function redirect(path) {
        setBreadcrumbs();
        window.location.href = '/private' + path;
    }

    function setBreadcrumbs() {
        let breadcrumbs = [];
        $('#headerView').find('ion-breadcrumb').each(function () {
            let breadcrumb = $(this);
            breadcrumbs.push({
                path: breadcrumb.attr('href'),
                label: breadcrumb.text()
            });
        });
        $.cookie('breadcrumbs', JSON.stringify(breadcrumbs), {path: '/'});
    }

    return {
        home: function (username, password) {
            $.cookie('auth', JSON.stringify({
                username: username.val(),
                password: password.val()
            }), {path: '/'});
            redirect('/home');
        },
        newSeason: function () {
            redirect('/new/season');
        }
    };
})();
