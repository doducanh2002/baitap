package org.up.finance.constant;

import static org.up.finance.constant.FinanceApiConstant.ApiConstant.API_PREFIX;
import static org.up.finance.constant.FinanceApiConstant.ApiConstant.API_VERSION;
import static org.up.finance.constant.FinanceApiConstant.ResourceConstant.*;

public class FinanceApiConstant {

    public static class ApiConstant {
        public static final String API_PREFIX = "/api";
        public static final String API_VERSION = "/v1";
    }

    public static class ResourceConstant {
        public static final String WALLET_GROUP_RESOURCE = "/wallet-groups";
        public static final String CATEGORY = "/categories";

        public static final String WALLET_RESOURCE = "/wallets";

        public static final String DAILY_NOTE_RESOURCE = "/daily-notes";

        public static final String TRANSACTION_RESOURCE = "/transactions";

    }

    public static class BaseUrl {
        public static final String WALLET_GROUP_URL = API_PREFIX + API_VERSION + WALLET_GROUP_RESOURCE;
        public static final String CATEGORY_BASE_URL = API_PREFIX + API_VERSION + CATEGORY;
        public static final String DAILY_NOTE_BASE_URL = API_PREFIX + API_VERSION + DAILY_NOTE_RESOURCE;
        public static final String TRANSACTION_BASE_URL = API_PREFIX + API_VERSION + TRANSACTION_RESOURCE;
    }
}
