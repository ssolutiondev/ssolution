package com.ssolution.admin.system.domain.upload;

public class UploadFileInfo
{
  String file_name;
  String file_ext;
  String file_type;
  String file_size;
  String file_path_name;
  String real_path;
  
  public String getFile_name()
  {
    return this.file_name;
  }
  
  public void setFile_name(String file_name)
  {
    this.file_name = file_name;
  }
  
  public String getFile_ext()
  {
    return this.file_ext;
  }
  
  public void setFile_ext(String file_ext)
  {
    this.file_ext = file_ext;
  }
  
  public String getFile_size()
  {
    return this.file_size;
  }
  
  public void setFile_size(String file_size)
  {
    this.file_size = file_size;
  }
  
  public String getFile_path_name()
  {
    return this.file_path_name;
  }
  
  public void setFile_path_name(String file_path_name)
  {
    this.file_path_name = file_path_name;
  }
  
  public String getReal_path()
  {
    return this.real_path;
  }
  
  public void setReal_path(String real_path)
  {
    this.real_path = real_path;
  }
  
  public String getFile_type()
  {
    return this.file_type;
  }
  
  public void setFile_type(String file_type)
  {
    this.file_type = file_type;
  }
  
  public String toString()
  {
    return "UploadFileInfo [file_name=" + this.file_name + ", file_ext=" + this.file_ext + ", file_type=" + this.file_type + ", file_size=" + this.file_size + ", file_path_name=" + this.file_path_name + ", real_path=" + this.real_path + "]";
  }
}
