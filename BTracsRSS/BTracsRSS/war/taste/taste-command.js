CmdUtils.CreateCommand({
    names: ["taste"],
    arguments: [
        { role: "object", nountype: noun_arb_text, label: "what" },
        { role: "alias", nountype: ["music", "movie", "book", "show"], label: "type" }
    ],
    author: "Leandro Aparecido",
    icon: "http://www.tastekid.com/favicon.ico",
    preview: function(pblock, arguments) {
        var searchText = arguments.object.text;
        if (searchText.length == 0) {
            showAlert("You need to type what you are interested in :p");
            return;
        }
        var typeText = arguments.alias.text;
        if (typeText.length == 0) {
            typeText = "music";
        }

        var baseUrl = "http://www.tastekid.com/ask/ws";
        var params = {
            q: searchText,
            verbose: 0,
            striptags: 0
        }

        var me = this;
        jQuery.ajax({
            type: "GET",
            url: baseUrl,
            data: params,
            dataType: "xml",
            error: function(request, textStatus, errorThrown) {
                me.showAlert("could not fetch recomendations: " + textStatus);
            },
            success: function(data, textStatus) {
                var result = "";
                jQuery(data).find("results").each(function(i) {
                    jQuery(this).find("resource").each(function(j) {
                        var resourceName = jQuery(this).find("name").text();
                        var typeName = jQuery(this).find("type").text();
                        if (typeText == typeName) {
                            result += resourceName + ", ";
                        }
                    });
                });
                pblock.innerHTML = result.substring(0, result.length - 2);
            }
        });
    },
    description: "Explore your taste",
    license: "MPL",
    execute: function(arguments) {
    },

    showAlert: function(txt){
        displayMessage({ icon: this.icon, title: this.name, text: txt });
    }
});
