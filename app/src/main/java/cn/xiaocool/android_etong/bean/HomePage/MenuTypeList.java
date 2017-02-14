package cn.xiaocool.android_etong.bean.HomePage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaocool on 17/2/13.
 */

public class MenuTypeList implements Serializable{


    /**
     * term_id : 1
     * name : 消费品
     * photo : t.jpg
     * haschild : 1
     * childlist : [{"term_id":"2","name":"日常","photo":"t.jpg","haschild":1,"childlist":[{"term_id":"3","name":"服装","photo":"t.jpg"},{"term_id":"4","name":"厨房用具","photo":"t.jpg"}]},{"term_id":"6","name":"第二级_2","photo":"t.jpg","haschild":1,"childlist":[{"term_id":"7","name":"第三级_3","photo":"t.jpg"}]}]
     */

    private String term_id;
    private String name;
    private String photo;
    private int haschild;
    private List<ChildlistBeanX> childlist;

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getHaschild() {
        return haschild;
    }

    public void setHaschild(int haschild) {
        this.haschild = haschild;
    }

    public List<ChildlistBeanX> getChildlist() {
        return childlist;
    }

    public void setChildlist(List<ChildlistBeanX> childlist) {
        this.childlist = childlist;
    }

    public static class ChildlistBeanX implements Serializable{
        /**
         * term_id : 2
         * name : 日常
         * photo : t.jpg
         * haschild : 1
         * childlist : [{"term_id":"3","name":"服装","photo":"t.jpg"},{"term_id":"4","name":"厨房用具","photo":"t.jpg"}]
         */

        private String term_id;
        private String name;
        private String photo;
        private int haschild;
        private List<ChildlistBean> childlist;

        public String getTerm_id() {
            return term_id;
        }

        public void setTerm_id(String term_id) {
            this.term_id = term_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getHaschild() {
            return haschild;
        }

        public void setHaschild(int haschild) {
            this.haschild = haschild;
        }

        public List<ChildlistBean> getChildlist() {
            return childlist;
        }

        public void setChildlist(List<ChildlistBean> childlist) {
            this.childlist = childlist;
        }

        public static class ChildlistBean implements Serializable{
            /**
             * term_id : 3
             * name : 服装
             * photo : t.jpg
             */

            private String term_id;
            private String name;
            private String photo;

            public String getTerm_id() {
                return term_id;
            }

            public void setTerm_id(String term_id) {
                this.term_id = term_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }
    }
}
