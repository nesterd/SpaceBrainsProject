using System;
using System.Collections.Generic;
using System.Data.Entity.ModelConfiguration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    public class PersonPageEntityTypeConfiguration
        : EntityTypeConfiguration<Person>
    {
        public PersonPageEntityTypeConfiguration()
        {

        }
    }
}
//public GroupEntityTypeConfiguration()
//{
//    HasMany(x => x.Roles)
//        .WithMany(x => x.Groups)
//        .Map(m =>
//        {
//            m.MapLeftKey("GroupId");
//            m.MapRightKey("RoleId");
//            m.ToTable("GroupRoles");
//        });