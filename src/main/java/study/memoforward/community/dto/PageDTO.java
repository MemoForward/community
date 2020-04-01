package study.memoforward.community.dto;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {
    private List<T> list;
    private boolean hasPre = true;
    private boolean hasNext = true;
    private Integer totalPage;
    private Integer curPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPage(Integer page, Integer totalPage) {
        this.totalPage = totalPage;
        this.curPage = page;
        if (page == 1) this.hasPre = false;
        if (page.equals(this.totalPage)) this.hasNext = false;
        int head = 3, end = 3;
        while (page - head < 1) head--;
        while (page + end > this.totalPage) end--;
        for (int i = page - head; i <= page + end; i++) pages.add(i);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "list=" + list +
                ", hasPre=" + hasPre +
                ", hasNext=" + hasNext +
                ", totalPage=" + totalPage +
                ", curPage=" + curPage +
                ", pages=" + pages +
                '}';
    }
}
