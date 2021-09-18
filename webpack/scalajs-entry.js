if (process.env.NODE_ENV === "production") {
    const opt = require("./pixeval-intro-opt.js");
    opt.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./pixeval-intro-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./pixeval-intro-fastopt.js");
    fastOpt.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
