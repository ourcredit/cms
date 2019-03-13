package com.monkey.common.base;

public interface PermissionConst {

    class _dashboard {
        public static final String show = "dashboard:show";
    }

    class _channel {
        public static final String show = "channel:show";

        public static class _channelList {
            public static final String list = "channels:list";
            public static final String modify = "channels:modify";
            public static final String delete = "channels:delete";
            public static final String batch = "channels:batch";
            public static final String first = "channels:first";
            public static final String visit = "channels:visit";
            public static final String lunch = "channels:lunch";
            public static final String createCustomer = "channels:createCustomer";
        }
    }

    class _relation {
        public static final String show = "relation:show";

        public static class _customer {
            public static final String list = "customer:list";
            public static final String modify = "customer:modify";
            public static final String delete = "customer:delete";
            public static final String batch = "customer:batch";
            public static final String first = "customer:first";
            public static final String share = "customer:share";
            public static final String giveto = "customer:giveto";
        }

        public static class _share {
            public static final String list = "share:list";
            public static final String modify = "share:modify";
            public static final String delete = "share:delete";
            public static final String batch = "share:batch";
            public static final String first = "share:first";
        }

        public static class _shareLog {
            public static final String list = "slog:list";
            public static final String modify = "share:modify";
            public static final String delete = "share:delete";
            public static final String batch = "share:batch";
            public static final String first = "slog:first";
        }

        public static class _force {
            public static final String list = "force:list";
            public static final String modify = "force:modify";
            public static final String delete = "force:delete";
            public static final String batch = "force:batch";
            public static final String first = "force:first";
            public static final String set = "force:set";
        }
    }

    class _contract {
        public static final String show = "contract:show";

        public static class _contractList {
            public static final String list = "contracts:list";
            public static final String modify = "contracts:modify";
            public static final String delete = "contracts:delete";
            public static final String batch = "contracts:batch";
            public static final String first = "contracts:first";
        }
        public static class _shareLog {
            public static final String list = "conLog:list";
            public static final String modify = "conLog:modify";
            public static final String delete = "conLog:delete";
            public static final String batch = "conLog:batch";
            public static final String first = "conLog:first";
        }
    }

    class _report {
        public static final String show = "report:show";

        public static class _a {
            public static final String list = "a:show";
        }

        public static class _b {
            public static final String list = "b:show";
        }

        public static class _c {
            public static final String list = "c:show";
        }

        public static class _d {
            public static final String list = "d:show";
        }

        public static class _e {
            public static final String list = "e:show";
        }

        public static class _f {
            public static final String list = "f:show";
        }
    }

    public static class _system {
        public static final String list = "system:show";

        public static class _category {
            public static final String list = "category:list";
            public static final String modify = "category:modify";
            public static final String delete = "category:delete";
            public static final String batch = "category:batch";
            public static final String first = "category:first";
        }

        public static class _user {
            public static final String list = "user:list";
            public static final String modify = "user:modify";
            public static final String delete = "user:delete";
            public static final String batch = "user:batch";
            public static final String first = "user:first";
        }

        public static class _role {
            public static final String list = "role:list";
            public static final String modify = "role:modify";
            public static final String delete = "role:delete";
            public static final String batch = "role:batch";
            public static final String first = "role:first";
        }

        public static class _menu {
            public static final String list = "menu:list";
            public static final String modify = "menu:modify";
            public static final String delete = "menu:delete";
            public static final String batch = "menu:batch";
            public static final String first = "menu:first";
        }

        public static class _org {
            public static final String list = "org:list";
            public static final String modify = "org:modify";
            public static final String delete = "org:delete";
            public static final String batch = "org:batch";
            public static final String first = "org:first";
        }
    }

}
