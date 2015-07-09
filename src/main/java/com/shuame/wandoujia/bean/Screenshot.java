package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonAutoDetect
@JsonPropertyOrder({ "position", "left", "top", "width", "height", "round" })
@JsonSerialize(include = Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Screenshot")
@XmlType(name = "Screenshot", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class Screenshot implements Cloneable {

    /** 位置类型 */
    // @XmlElement(name = "position")
    @XmlTransient
    private String _position;

    /** 左边框坐标 */
    @XmlElement(name = "left")
    private String _left;

    /** 上边框坐标 */
    @XmlElement(name = "top")
    private String _top;

    /** 截屏宽度 */
    @XmlElement(name = "width")
    private String _width;

    /** 截屏高度 */
    @XmlElement(name = "height")
    private String _height;

    /** 屏幕是否是圆形.1:圆形;0:方形。圆形屏幕设备（譬如智能手表）的屏幕截图也是方形的，因而需要区别矩形屏幕显示。 */
    @XmlElement(name = "round")
    private int _round;

    /** 右边框坐标 */
    @XmlTransient
    private String _right;

    /** 底边框坐标 */
    @XmlTransient
    private String _bottom;

    public Screenshot() {
    }

    public Screenshot(String left, String top, String width, String height) {
        _left = left;
        _top = top;
        _width = width;
        _height = height;
    }

    public Screenshot(String left, String top, String width, String height, int round) {
        _left = left;
        _top = top;
        _width = width;
        _height = height;
        _round = round;
    }

    public Screenshot(String... strings) {
        if (strings != null) {
            _left = strings.length > 0 ? strings[0] : "";
            _top = strings.length > 1 ? strings[1] : "";
            _width = strings.length > 2 ? strings[2] : "";
            _height = strings.length > 3 ? strings[3] : "";

            if (strings.length > 5) { // 若有右/底边框的坐标
                _right = strings[4];
                _bottom = strings[5];

                if (StringUtils.isBlank(_width) && StringUtils.isBlank(_height)) { // 而且还没有宽度和高度的数据
                    // 则根据左上点和右下点的坐标计算出宽度和高度
                    int left = NumberUtils.toInt(_left);
                    int top = NumberUtils.toInt(_top);
                    int right = NumberUtils.toInt(_right);
                    int bottom = NumberUtils.toInt(_bottom);

                    _width = String.valueOf(right - left);
                    _height = String.valueOf(bottom - top);
                }
            }

            // 新增加的Round属性，用来表示手机屏幕截图是否需要使用圆形显示.
            if (strings.length > 6) {
                String roundString = strings[6];
                if (StringUtils.isNotBlank(roundString)) {
                    _round = Integer.parseInt(roundString);
                }
            }
        }
    }

    // Getter & Setter

    public String getPosition() {
        return _position;
    }

    public void setPosition(String position) {
        _position = position;
    }

    public String getLeft() {
        return _left;
    }

    public void setLeft(String left) {
        _left = left;
    }

    public String getTop() {
        return _top;
    }

    public void setTop(String top) {
        _top = top;
    }

    public String getWidth() {
        return _width;
    }

    public void setWidth(String width) {
        _width = width;
    }

    public String getHeight() {
        return _height;
    }

    public void setHeight(String height) {
        _height = height;
    }

    public String getRight() {
        return _right;
    }

    public void setRight(String right) {
        _right = right;
    }

    public String getBottom() {
        return _bottom;
    }

    public void setBottom(String bottom) {
        _bottom = bottom;
    }

    public int getRound() {
        return _round;
    }

    public void setRound(int round) {
        _round = round;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    protected Screenshot clone() throws CloneNotSupportedException {
        return (Screenshot) super.clone();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_bottom == null) ? 0 : _bottom.hashCode());
        result = prime * result + ((_height == null) ? 0 : _height.hashCode());
        result = prime * result + ((_left == null) ? 0 : _left.hashCode());
        result = prime * result + ((_right == null) ? 0 : _right.hashCode());
        result = prime * result + ((_top == null) ? 0 : _top.hashCode());
        result = prime * result + ((_width == null) ? 0 : _width.hashCode());
        result = prime * result + _round;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Screenshot other = (Screenshot) obj;
        if (_bottom == null) {
            if (other._bottom != null) {
                return false;
            }
        } else if (!_bottom.equals(other._bottom)) {
            return false;
        }
        if (_height == null) {
            if (other._height != null) {
                return false;
            }
        } else if (!_height.equals(other._height)) {
            return false;
        }
        if (_left == null) {
            if (other._left != null) {
                return false;
            }
        } else if (!_left.equals(other._left)) {
            return false;
        }
        if (_right == null) {
            if (other._right != null) {
                return false;
            }
        } else if (!_right.equals(other._right)) {
            return false;
        }
        if (_top == null) {
            if (other._top != null) {
                return false;
            }
        } else if (!_top.equals(other._top)) {
            return false;
        }
        if (_width == null) {
            if (other._width != null) {
                return false;
            }
        } else if (!_width.equals(other._width)) {
            return false;
        }

        return _round == other._round;
    }

}
